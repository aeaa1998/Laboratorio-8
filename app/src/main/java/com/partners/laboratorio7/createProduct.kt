package com.partners.laboratorio7

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.partners.laboratorio7.database.AppDatabase
import com.partners.laboratorio7.database.Products
import com.partners.laboratorio7.ui.inventories.InventoriesViewModel
import com.partners.laboratorio7.ui.products.ProductsViewModel
import com.partners.laboratorio7.ui.products.ProductsViewModelFactory
import kotlinx.android.synthetic.main.fragment_create_inventary.*
import kotlinx.android.synthetic.main.fragment_create_product.*


class createProduct : Fragment() {
lateinit var viewModel:ProductsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val btnFab = container?.rootView?.findViewById<View>(R.id.fab)
        btnFab?.visibility = View.GONE
        btnFab?.isClickable = false
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_create_product, container, false)
        val btn = v.findViewById<View>(R.id.send_button)
        val application = requireNotNull(this.activity).application
        val dataSource = AppDatabase.getInstance(application).appDao
        val viewModelFactory = ProductsViewModelFactory(dataSource)
        val viewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(ProductsViewModel::class.java)

        btn.setOnClickListener {


            if (product_name.text.isEmpty() || product_code.text.isEmpty()){
                Toast.makeText(context, "Llene los campo", Toast.LENGTH_LONG).show()
            }else{
                var p = dataSource.checkCodeExists(product_code.text.toString())
                if (p != null){
                    Toast.makeText(context, "Ese codigo ya existe", Toast.LENGTH_LONG).show()
                }
                else{
                    var newp = Products()
                    newp.code = product_code.text.toString()
                    newp.name = product_name.text.toString()
                    viewModel.addProduct(newp)
                    view?.clearFocus()
                    Toast.makeText(context, "Se ah creado el producto", Toast.LENGTH_LONG).show()
                    NavHostFragment.findNavController(this).navigateUp()
                }
            }
        }
        return v
    }



}
