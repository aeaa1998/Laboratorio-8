package com.partners.laboratorio7.ui.inventories

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.NavAction
import androidx.navigation.NavArgs
import androidx.navigation.NavHost
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.partners.laboratorio7.Adapters.ViewHolder.CustomViewHolder
import com.partners.laboratorio7.App.Companion.indexInventary
import com.partners.laboratorio7.R
import com.partners.laboratorio7.database.AppDatabase
import com.partners.laboratorio7.database.InventaryDb
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.multiple_inventaries_row.view.*

class InventoriesFragment : Fragment() {
    private lateinit var recycler: RecyclerView
    companion object {
        fun newInstance() = InventoriesFragment()
    }
    var inventories: ArrayList<InventaryDb> = arrayListOf()

    private lateinit var viewModel: InventoriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v = inflater.inflate(R.layout.inventories_fragment, container, false)
        recycler = v.findViewById(R.id.recycler_container_inventories) as RecyclerView
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = InventoriesAdapter()
        recycler.adapter?.notifyDataSetChanged()
        val btn = container?.rootView?.findViewById<View>(R.id.fab)
        btn?.visibility = View.VISIBLE
        btn?.isClickable = true


        btn?.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_inventories_fragment_to_create_inventary)
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

        val viewModelFactory = InventoriesViewModelFactory(dataSource)
        viewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(InventoriesViewModel::class.java)

        viewModel.inventories.observe(this, Observer {
            inventories = it
            recycler.adapter?.notifyDataSetChanged()
        })
    }
    inner class InventoriesAdapter: RecyclerView.Adapter<CustomViewHolder>() {
        override fun getItemCount() = AppDatabase.getInstance(requireNotNull(activity).application).appDao.getAllInventories().size

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            holder.view.inventary_info.text = viewModel.inventories.value?.get(position)?.name ?: ""
            holder.view.inventary_button.setOnClickListener {
                indexInventary = position
                val selected = viewModel.inventories.value?.get(position)
                val action = InventoriesFragmentDirections.actionInventoriesFragmentToSingleInventaryFragment(viewModel.inventories.value?.get(position)!!.id)
                findNavController(parentFragment!!).navigate(action)

            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CustomViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val cellForRow = layoutInflater.inflate(R.layout.multiple_inventaries_row, parent, false)

            return CustomViewHolder(cellForRow)

        }

    }

}


