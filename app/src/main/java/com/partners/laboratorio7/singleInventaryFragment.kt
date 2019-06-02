package com.partners.laboratorio7

import android.content.Intent
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.partners.laboratorio7.Adapters.ViewHolder.CustomViewHolder
import com.partners.laboratorio7.database.AppDatabase
import com.partners.laboratorio7.database.RowDb
import com.partners.laboratorio7.ui.inventories.InventoriesFragmentArgs
import com.partners.laboratorio7.ui.inventories.InventoriesFragmentDirections
import com.partners.laboratorio7.ui.inventories.InventoriesViewModel
import com.partners.laboratorio7.ui.inventories.InventoriesViewModelFactory
import kotlinx.android.synthetic.main.card_view_row.*
import kotlinx.android.synthetic.main.card_view_row.view.*
import kotlinx.android.synthetic.main.multiple_inventaries_row.view.*


class singleInventaryFragment : Fragment() {
    var selectedInventory: Int = 0
    lateinit var rows: ArrayList<RowDb>
    lateinit var recycler: RecyclerView
    lateinit var viewModelAll: InventoriesViewModel

    companion object {
        fun newInstance() = singleInventaryFragment()
    }

    private lateinit var viewModel: SingleInventaryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v =inflater.inflate(R.layout.single_inventary_fragment, container, false)
        recycler = v.findViewById(R.id.inventory_rows_recycler)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = SingleInventoryAdapter()
        recycler.adapter?.notifyDataSetChanged()
        val btn = container?.rootView?.findViewById<View>(R.id.fab)
        btn?.visibility = View.VISIBLE
        btn?.isClickable = true


        btn?.setOnClickListener {
            if (ContextCompat.checkSelfPermission(activity!!.applicationContext, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity!!,
                    arrayOf(android.Manifest.permission.CAMERA),
                    1)
            }else{
                val action = singleInventaryFragmentDirections.actionSingleInventaryFragmentToScanCodeFragment(selectedInventory)
                NavHostFragment.findNavController(parentFragment!!).navigate(action)
            }
        }

        return v
    }

    override fun onResume() {
        viewModelAll.reset()
        viewModel.setRows(selectedInventory)
        super.onResume()
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        selectedInventory = singleInventaryFragmentArgs.fromBundle(arguments!!).selectedInventary

        val application = requireNotNull(this.activity).application
        val dataSource = AppDatabase.getInstance(application).appDao
        val singleFactory = singleInventaryFactory(selectedInventory, dataSource)

        viewModel =
            ViewModelProviders.of(
                this, singleFactory).get(SingleInventaryViewModel::class.java)
        val inventoriesFactory = InventoriesViewModelFactory(dataSource)
        viewModelAll =
            ViewModelProviders.of(
                this, inventoriesFactory).get(InventoriesViewModel::class.java)

        viewModel.setRows(selectedInventory)
        viewModel.rows.value

        viewModel.rows.observe(this, Observer {
            rows = it
            recycler.adapter?.notifyDataSetChanged()
        })

    }

    inner class SingleInventoryAdapter: RecyclerView.Adapter<CustomViewHolder>() {
        override fun getItemCount() = AppDatabase.getInstance(requireNotNull(activity).application).appDao.getAllRowsByInv(selectedInventory).size ?: 0
        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            val application = requireNotNull(activity).application
            val dataSource = AppDatabase.getInstance(application).appDao
            val row = viewModel.rows.value!![position]
            val p = dataSource.getProduct(row.productId)
            holder.view.name_producr.text = p!!.name
            holder.view.quantity_product.text = row.quantity.toString()
            holder.view.plus_quantity.setOnClickListener {
                viewModel.rows.value!![position].quantity++
                dataSource.updateInv(selectedInventory, row.id)
                viewModelAll.reset()
                holder.view.quantity_product.text = row.quantity.toString()
            }
            holder.view.minus_quantity.setOnClickListener {
                val q = row.quantity.minus(1)
                if(q >= 0){
                    viewModel.rows.value!![position].quantity--
                    dataSource.updateInv(selectedInventory, row.id)
                    viewModelAll.reset()
                    holder.view.quantity_product.text = row.quantity.toString()
                }else{
                    Toast.makeText(context, "No se puede tener una cantidad negativa de productos", Toast.LENGTH_LONG).show()

                }

            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CustomViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
//            val binding = DataBindingUtil.inflate<RecyclerRowBinding>(layoutInflater, R.layout.recycler_row, p0, false)
//            binding.product = ProductBinding(row.getProduct().getName(), row.getQuantity())
            val cellForRow = layoutInflater.inflate(R.layout.card_view_row, parent, false)

            return CustomViewHolder(cellForRow)

        }

    }

}


