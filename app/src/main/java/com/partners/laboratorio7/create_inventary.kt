package com.partners.laboratorio7

import android.content.Context
import android.inputmethodservice.Keyboard
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.partners.laboratorio7.Models.Inventary
import com.partners.laboratorio7.database.AppDatabase
import com.partners.laboratorio7.database.InventaryDb
import com.partners.laboratorio7.ui.inventories.InventoriesViewModel
import com.partners.laboratorio7.ui.inventories.InventoriesViewModelFactory
import kotlinx.android.synthetic.main.fragment_create_inventary.*
import kotlinx.android.synthetic.main.fragment_create_inventary.view.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [create_inventary.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [create_inventary.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class create_inventary : Fragment() {
    private lateinit var viewModel: InventoriesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val btnFab = container?.rootView?.findViewById<View>(R.id.fab)
        btnFab?.visibility = View.GONE
        btnFab?.isClickable = false
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_create_inventary, container, false)
        val btn = v.findViewById<View>(R.id.button_send)
        val application = requireNotNull(this.activity).application
        val dataSource = AppDatabase.getInstance(application).appDao

        val viewModelFactory = InventoriesViewModelFactory(dataSource)
        val viewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(InventoriesViewModel::class.java)
        btn.setOnClickListener {
            if (name.text.isEmpty()){
                Toast.makeText(context, "Llene le campo", Toast.LENGTH_LONG).show()
            }else{
                var formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy")
                var inv = InventaryDb()
                inv.name = name.text.toString()
                viewModel.onAddRow(inv)
                view?.clearFocus()
                Toast.makeText(context, "Se ah creado el inventario", Toast.LENGTH_LONG).show()
                NavHostFragment.findNavController(this).navigateUp()
            }
        }
        return v
    }





}
