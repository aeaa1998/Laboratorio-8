package com.partners.laboratorio7.ui.products

import android.opengl.Visibility
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.partners.laboratorio7.Adapters.ViewHolder.CustomViewHolder
import com.partners.laboratorio7.Models.Inventary
import com.partners.laboratorio7.Models.Product
import com.partners.laboratorio7.R
import com.partners.laboratorio7.database.AppDatabase
import com.partners.laboratorio7.database.Products
import com.partners.laboratorio7.ui.inventories.InventoriesViewModel
import com.partners.laboratorio7.ui.inventories.InventoriesViewModelFactory
import kotlinx.android.synthetic.main.multiple_inventaries_row.view.*

class ProductsFragment : Fragment() {
    private lateinit var recycler: RecyclerView
    companion object {
        fun newInstance() = ProductsFragment()

    }

    private lateinit var viewModel: ProductsViewModel
    var products: ArrayList<Products> = arrayListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val v = inflater.inflate(R.layout.inventories_fragment, container, false)
        recycler = v.findViewById(R.id.recycler_container_inventories)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = ProductsAdapter()
        recycler.adapter?.notifyDataSetChanged()
        val btn = container?.rootView?.findViewById<View>(R.id.fab)
        btn?.visibility = View.VISIBLE


        btn?.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_products_fragment_to_createProduct)

        }
        return v
    }
    override fun onResume() {
        super.onResume()
        viewModel.reset()
        recycler.adapter?.notifyDataSetChanged()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val application = requireNotNull(this.activity).application
        val dataSource = AppDatabase.getInstance(application).appDao
        val viewModelFactory = ProductsViewModelFactory(dataSource)
        viewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(ProductsViewModel::class.java)

        viewModel.products.observe(this, Observer {
            products = it
            recycler.adapter?.notifyDataSetChanged()
        })
    }
    inner class ProductsAdapter: RecyclerView.Adapter<CustomViewHolder>() {
        override fun getItemCount() = AppDatabase.getInstance(requireNotNull(activity).application).appDao.getAllProducts().size

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            holder.view.inventary_info.text = viewModel.products.value?.get(position)?.name ?: ""
            holder.view.inventary_button.setOnClickListener {
                Toast.makeText(context,"YEAHHHH", Toast.LENGTH_LONG).show()
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CustomViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val cellForRow = layoutInflater.inflate(R.layout.multiple_inventaries_row, parent, false)
            cellForRow.findViewById<View>(R.id.inventary_button).isClickable = false
            cellForRow.findViewById<View>(R.id.inventary_button).visibility = View.GONE

            return CustomViewHolder(cellForRow)

        }

    }

}
