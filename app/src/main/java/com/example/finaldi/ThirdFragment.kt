package com.example.finaldi

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


class ThirdFragment : Fragment() {

    lateinit var selCategoria: String
    lateinit var etTitulo: EditText
    lateinit var tvCat: TextView


    lateinit var etDificultad: EditText
    lateinit var etTiempo: EditText
    lateinit var etIngredientes: EditText
    lateinit var etPasos: EditText
    var pos = -2

    lateinit var mireceta: RoomReceta

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)


        etTitulo = view.findViewById<EditText>(R.id.fragment3_et_titulo)
        tvCat = view.findViewById<TextView>(R.id.fragment3_tv_labcategoria)
        etDificultad = view.findViewById<EditText>(R.id.fragment3_et_dificultad)
        etTiempo = view.findViewById<EditText>(R.id.fragment3_et_tiempo)
        etIngredientes = view.findViewById<EditText>(R.id.fragment3_mline_ingregientes)
        etPasos = view.findViewById<EditText>(R.id.fragment3_mline_descripcion)



        pos = arguments?.getInt("id") ?: -1

        val btCategoria = view.findViewById<Button>(R.id.fragment3_bt_categoria)

        btCategoria.setOnClickListener {
            // Show the single choice list items on an alert dialog
            showCatDialog()
        }

        if (pos == -1) {//viene de crear

            activity?.setTitle("Nueva")

        } else { //viene de modificar o eliminar

            activity?.setTitle("Edita")

            (activity as MainActivity).myViewModel.BuscarPorId(pos)
            (activity as MainActivity).myViewModel.miReceta.observe(activity as MainActivity) {
                it?.let {
                    Log.d("tercero", it.titulo)// saca en el logcat info
                    mireceta = it
                    etTitulo.setText(it.titulo)
                    selCategoria = it.categoria

                    etDificultad.setText(it.dificultad.toString())
                    etTiempo.setText(it.tiempo.toString())
                    etIngredientes.setText(it.ingredientes)
                    etPasos.setText(it.pasos)
                }

                tvCat.setText(selCategoria)
            }

        }


    }

    fun validar(): String {

        val titulo = etTitulo.text.toString()
        val tiempo: String = etTiempo.text.toString()
        val dificultad = etDificultad.text.toString()
        val ingredientes = etIngredientes.text.toString()
        val pasos = etPasos.text.toString()

        var errores = ""


        if (titulo.isEmpty()) {
            errores += "Debes darle un titulo a tu receta.\n"
        }

        if (tiempo.isEmpty()) {
            errores += "Le debes asignar un tiempo a tu receta!\n"
        } else {
            if ((tiempo.toInt() > 240) or (tiempo.toInt() < 5)) {
                errores += "El tiempo debe estar entre 5 y 240 minutos.\n"
            }
        }



        if (dificultad.isEmpty()) {
            errores += "La dificultad es un dato necesario...\n"
        } else {
            if ((dificultad.toInt() > 10) or (dificultad.toInt() < 1)) {
                errores += "La dificultad debe estar entre 1 y 10.\n"
            }
        }



        if (ingredientes.isEmpty()) {
            errores += "Sin ingredientes no hay receta.\n"
        }

        if (pasos.isEmpty()) {
            errores += "Sin pasos a seguir, no puedes guardar una receta."
        }

        return errores


    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.menuBorrar)?.isVisible = true
        menu.findItem(R.id.menuGuardar)?.isVisible = true
        menu.findItem(R.id.menuEditar)?.isVisible = false
        menu.findItem(R.id.menInsertar)?.isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuBorrar -> {
                if (pos == -1) {

                    etTitulo.setText("")

                    tvCat.setText("Selecciona categoria")
                    etDificultad.setText("")
                    etTiempo.setText("")
                    etIngredientes.setText("")
                    etPasos.setText("")

                } else {
                    showAlertDialog()
                }

            }

            R.id.menuGuardar -> {

                if (pos == -2) {
                    Toast.makeText(activity, "Algo no va bien..", Toast.LENGTH_SHORT).show()
                } // algo ha salido mal
                else if (pos == -1) { // vengo de insertar
                    val errores = validar()

                    if (errores == "") {

                        (activity as MainActivity).myViewModel.Insertar(
                            miReceta = RoomReceta(
                                titulo = etTitulo.text.toString(),
                                categoria = selCategoria,
                                //categoria = etCategoria.text.toString(),
                                dificultad = etDificultad.text.toString().toInt(),
                                tiempo = etTiempo.text.toString().toInt(),
                                ingredientes = etIngredientes.text.toString(),
                                pasos = etPasos.text.toString(),

                                )
                        )

                        findNavController().navigate(R.id.action_thirdFragment_to_SecondFragment)

                    } else {
                        Toast.makeText(activity, errores, Toast.LENGTH_SHORT)
                            .show()
                    }
                } // vengo de modificar o eliminar
                else {
                    val errores = validar()

                    if (errores == "") {

                        (activity as MainActivity).myViewModel.Actualizar(
                            miReceta = RoomReceta(
                                pos,
                                titulo = etTitulo.text.toString(),
                                categoria = selCategoria,
                                //categoria = etCategoria.text.toString(),
                                dificultad = etDificultad.text.toString().toInt(),
                                tiempo = etTiempo.text.toString().toInt(),
                                ingredientes = etIngredientes.text.toString(),
                                pasos = etPasos.text.toString(),
                            )
                        )

                        findNavController().navigate(R.id.action_thirdFragment_to_SecondFragment)

                    } else {
                        Toast.makeText(activity, errores, Toast.LENGTH_SHORT)
                            .show()

                    }
                }


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
            findNavController().navigate(R.id.action_thirdFragment_to_SecondFragment)
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

    // Method to show an alert dialog with single choice list items
    private fun showCatDialog() {
        // Late initialize an alert dialog object
        lateinit var dialog: AlertDialog

        // Initialize an array of colors
        val array = arrayOf("INDEFINIDO", "ENTRANTE", "PASTA", "PIZZA", "COCIDO", "HORNO", "POSTRE")

        // Initialize a new instance of alert dialog builder object
        val builder = AlertDialog.Builder(requireActivity())

        // Set a title for alert dialog
        builder.setTitle("Elige una Categoria: .")


        // Set the single choice items for alert dialog with initial selection
        builder.setSingleChoiceItems(array, 0) { _, which ->
            // Get the dialog selected item
            val categoria = array[which]

            // Try to parse user selected color string
            try {
                selCategoria = categoria
                tvCat.setText(selCategoria)

            } catch (e: IllegalArgumentException) {
                // Catch the color string parse exception
                Toast.makeText(activity, "Algo no va bien..", Toast.LENGTH_SHORT).show()
            }

            // Dismiss the dialog
            dialog.dismiss()
        }


        // Initialize the AlertDialog using builder object
        dialog = builder.create()

        // Finally, display the alert dialog
        dialog.show()
    }


}
