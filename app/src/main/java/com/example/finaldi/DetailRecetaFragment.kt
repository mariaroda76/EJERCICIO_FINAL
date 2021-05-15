package com.example.finaldi

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController


class DetailRecetaFragment : Fragment() {

    var pos = -2
    lateinit var mireceta: RoomReceta

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_receta, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        val tvTitulo = view.findViewById<TextView>(R.id.tv_detailReceta_titulo)
        val tvCategoria = view.findViewById<TextView>(R.id.tv_detailReceta_categoria)
        val tvDificultad = view.findViewById<TextView>(R.id.tv_detailReceta_dificultad)
        val tvTiempo = view.findViewById<TextView>(R.id.tv_detailReceta_tiempo)
        val tvIngredientes = view.findViewById<TextView>(R.id.multiLine_ingredientes)
        val tvPasos = view.findViewById<TextView>(R.id.multiline_descripcion)

        tvPasos .setTag(tvPasos .getKeyListener())
        tvPasos .setKeyListener(null)

        tvIngredientes.setTag(tvIngredientes.getKeyListener())
        tvIngredientes.setKeyListener(null)

        pos = arguments?.getInt("id") ?: -1



        if (pos == -1) {//viene de crear

            activity?.setTitle("Nueva Receta")//no implementado solo puedo venir  de editar

        } else { //viene de modificar o eliminar

            activity?.setTitle("Receta")

            (activity as MainActivity).myViewModel.BuscarPorId(pos)
            (activity as MainActivity).myViewModel.miReceta.observe(activity as MainActivity) {
                it?.let {
                    Log.d("detail", it.titulo)// saca en el logcat info
                    mireceta = it
                    tvTitulo.setText(it.titulo)
                    tvCategoria.setText(it.categoria)
                    tvDificultad.setText(it.dificultad.toString())
                    tvTiempo.setText(it.tiempo.toString())
                    tvIngredientes.setText(it.ingredientes)
                    tvPasos.setText(it.pasos)


                }
            }
            
        }

    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.menuBorrar)?.isVisible=true
        menu.findItem(R.id.menuGuardar)?.isVisible=false
        menu.findItem(R.id.menuEditar)?.isVisible=true
        menu.findItem(R.id.menInsertar)?.isVisible=false
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuBorrar -> {
                showAlertDialog()
            }

            R.id.menuEditar-> {
                val bundle = bundleOf("id" to this.pos)
                findNavController().navigate(
                    R.id.action_detailRecetaFragment_to_thirdFragment2,
                    bundle
                )
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showAlertDialog() {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
        alertDialog.setTitle("AlertDialog")
        alertDialog.setMessage("Deseas borrar definitivamente la receta?")
        alertDialog.setPositiveButton(
            "Si"
        ) { _, _ ->

            (activity as MainActivity).myViewModel.Borrar(
                mireceta
            )
            findNavController().navigate(R.id.action_detailRecetaFragment_to_SecondFragment)
        }
        alertDialog.setNegativeButton(
            "No"
        ) { _, _ ->
            Toast.makeText(activity, "Has decidido no borrar la receta", Toast.LENGTH_SHORT).show()
        }
        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }



}