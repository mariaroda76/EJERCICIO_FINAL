package com.example.finaldi

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController


class UserFragment : Fragment() {

    lateinit var  etUsuario: EditText
    lateinit var  etPass: EditText
    lateinit var datos :SharedPreferences



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        val btSignIn = view.findViewById<Button>(R.id.user_bt_Sign)

        etUsuario= view.findViewById(R.id.et_UserFr_editUser)
        etPass= view.findViewById(R.id.et_UserFr_editPass)


        btSignIn.setOnClickListener {
            singIn(etUsuario,etPass)
        }

    }

    private fun singIn(etUsuario: EditText,etPass:EditText){

        datos = (activity as MainActivity).getSharedPreferences(
            "datos",
            Context.MODE_PRIVATE
        )

        if ((etUsuario.text.isNotEmpty()) or (etPass.text.isNotEmpty())) {

            val validUser = datos.getString("user",null)

            if (validUser==null) { //si no habia usuario inicial, los creo

                crearUser()
                //vuelvo al logIn creo que es mas correcto
                findNavController().navigate(R.id.action_userFragment_to_FirstFragment)
            }
            else if (etUsuario.getText().toString().equals(validUser)){//si el user introducido ya existe aviso
                showAlertDialog()
            } else {
                crearUser()
                //vuelvo al logIn creo que es mas correcto
                findNavController().navigate(R.id.action_userFragment_to_FirstFragment)
            }

        } else {
            Toast.makeText(activity, "Usuario y contraseña no deben quedar vacios!!", Toast.LENGTH_SHORT).show()
        }

    }

    private fun crearUser(){

        val user: String = etUsuario.text.toString().trim()
        val pass: String = etPass.text.toString().trim()

        val editor = datos.edit()
        editor.putString("user",user)
        editor.putString("pass",pass)

        editor.apply()
        Toast.makeText(activity, "Usuario:" + user + " y contraseña:" + pass , Toast.LENGTH_SHORT).show()
    }

    private fun showAlertDialog() {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
        alertDialog.setTitle("AlertDialog")
        alertDialog.setMessage("El usuario existe, deseas cambiar la contraseña?")
        alertDialog.setPositiveButton(
            "Si"
        ) { _, _ ->

            crearUser()
            //vuelvo al logIn creo que es mas correcto
            findNavController().navigate(R.id.action_userFragment_to_FirstFragment)
        }
        alertDialog.setNegativeButton(
            "No"
        ) { _, _ ->
            Toast.makeText(activity, "Has decidido no cambiar usuario y contraseña", Toast.LENGTH_SHORT).show()
        }
        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.menuBorrar)?.isVisible=false
        menu.findItem(R.id.menuGuardar)?.isVisible=false
        menu.findItem(R.id.menuEditar)?.isVisible=false
        menu.findItem(R.id.menInsertar)?.isVisible=false
    }



}