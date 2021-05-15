package com.example.finaldi

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    lateinit var miRecyclerView:RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView= inflater.inflate(R.layout.fragment_second, container, false)

        //maider clase
        val lista: List<RoomReceta> = listOf()

        (activity as MainActivity).myViewModel.listaRecetas.observe(activity as MainActivity){listaRecetas->
            listaRecetas?.let{
                //val listaRecetas=cargarRecetas()
                miRecyclerView=rootView.findViewById(R.id.fragmento2_lista_recyclerView)
                miRecyclerView.layoutManager=LinearLayoutManager(activity)
                miRecyclerView.adapter=Adaptador(it,(activity as MainActivity))
            }

        }

            rootView.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener{
                findNavController().navigate(R.id.action_SecondFragment_to_thirdFragment)


        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        activity?.setTitle("Mis Recetas")

    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.menuBorrar)?.isVisible=false
        menu.findItem(R.id.menuGuardar)?.isVisible=false
        menu.findItem(R.id.menuEditar)?.isVisible=false
        menu.findItem(R.id.menInsertar)?.isVisible=true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menInsertar->findNavController().navigate(R.id.action_SecondFragment_to_thirdFragment)
        }

        return super.onOptionsItemSelected(item)
    }

}