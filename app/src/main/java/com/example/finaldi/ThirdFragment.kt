package com.example.finaldi

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


class ThirdFragment : Fragment() {

    lateinit var etTitulo: EditText
    lateinit var etCategoria: EditText
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


/*        val btInsertar = view.findViewById<Button>(R.id.fragment3_bt_insert)
        val btModificar = view.findViewById<Button>(R.id.fragment3_bt_modificar)
        val btEliminar = view.findViewById<Button>(R.id.fragment3_bt_eliminar)*/

        etTitulo = view.findViewById<EditText>(R.id.fragment3_et_titulo)
        etCategoria = view.findViewById<EditText>(R.id.fragment3_et_categoria)
        etDificultad = view.findViewById<EditText>(R.id.fragment3_et_dificultad)
        etTiempo = view.findViewById<EditText>(R.id.fragment3_et_tiempo)
        etIngredientes = view.findViewById<EditText>(R.id.fragment3_mline_ingregientes)
        etPasos = view.findViewById<EditText>(R.id.fragment3_mline_descripcion)

        pos = arguments?.getInt("id") ?: -1



        if (pos == -1) {//viene de crear
/*            btEliminar.isEnabled = false
            btModificar.isEnabled = false
            btInsertar.isEnabled = false*/
            activity?.setTitle("Nueva")

        } else { //viene de modificar o eliminar
/*            btEliminar.isEnabled = false
            btModificar.isEnabled = false
            btInsertar.isEnabled = false*/
            activity?.setTitle("Edita")

            (activity as MainActivity).myViewModel.BuscarPorId(pos)
            (activity as MainActivity).myViewModel.miReceta.observe(activity as MainActivity) {
                it?.let {
                    Log.d("tercero", it.titulo)// saca en el logcat info
                    mireceta = it
                    etTitulo.setText(it.titulo)
                    etCategoria.setText(it.categoria)
                    etDificultad.setText(it.dificultad.toString())
                    etTiempo.setText(it.tiempo.toString())
                    etIngredientes.setText(it.ingredientes)
                    etPasos.setText(it.pasos)
                }
            }

        }

/*        btInsertar.setOnClickListener {

            val errores = validar()

            if (errores == "") {

                (activity as MainActivity).myViewModel.Insertar(
                    miReceta = RoomReceta(
                        titulo = etTitulo.text.toString(),
                        categoria = etCategoria.text.toString(),
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


        btModificar.setOnClickListener {
            val errores = validar()

            if (errores == "") {

                (activity as MainActivity).myViewModel.Actualizar(
                    miReceta = RoomReceta(
                        pos,
                        titulo = etTitulo.text.toString(),
                        categoria = etCategoria.text.toString(),
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

        btEliminar.setOnClickListener {

            (activity as MainActivity).myViewModel.Borrar(
                mireceta
            )


            findNavController().navigate(R.id.action_thirdFragment_to_SecondFragment)
        }*/


    }

    fun validar(): String {

        val titulo = etTitulo.text.toString()
        val categoria = etCategoria.text.toString()
        val tiempo: String = etTiempo.text.toString()
        val dificultad = etDificultad.text.toString()
        val ingredientes = etIngredientes.text.toString()
        val pasos = etPasos.text.toString()

        var errores = ""


        if (titulo.isEmpty()) {
            errores += "Debes darle un titulo a tu receta.\n"
        }
        if (categoria.isEmpty()) {
            errores += "La categoría no puede quedar en blanco.\n"
        }

        if (tiempo.isEmpty()) {
            errores += "La debes asignar un tiempo a tu receta!\n"
        }

        if (dificultad.isEmpty()) {
            errores += "La dificultad es un dato necesario...\n"
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
                if (pos == -1) {Toast.makeText(activity, "no han nada que borrar", Toast.LENGTH_SHORT)
                    .show()}
                else{
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
                                categoria = etCategoria.text.toString(),
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
                                categoria = etCategoria.text.toString(),
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
                } //vengo de detailed view para editar


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


}
