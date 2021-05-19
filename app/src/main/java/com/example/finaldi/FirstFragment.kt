package com.example.finaldi

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {


    lateinit var  etUsuario: EditText
    lateinit var  etPass: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        val btLogin = view.findViewById<Button>(R.id.first_bt_LogIn)
        val btSignIn = view.findViewById<Button>(R.id.fist_bt_SingIn)

        etUsuario= view.findViewById(R.id.et_First_user)
        etPass= view.findViewById(R.id.et_first_pass)


        btLogin.setOnClickListener {

            loginCheck()

        }

        btSignIn.setOnClickListener{
            findNavController().navigate(R.id.action_FirstFragment_to_userFragment)
        }

    }

    private fun loginCheck() {

        var user: String = etUsuario.text.toString().trim()
        var pass: String = etPass.text.toString().trim()



        if ((etUsuario.text.isNotEmpty()) or (etPass.text.isNotEmpty())) {
            val datos: SharedPreferences = (activity as MainActivity).getSharedPreferences(
                "datos",
                Context.MODE_PRIVATE
            )



            val validUser = datos.getString("user",null)
            val validPass = datos.getString("pass",null)

            if (validUser==null) {//si no hay user devuelve null (es poruqe es la primera vez que entro, entonces guardo los datos que haya y navego)
                val editor = datos.edit()
                editor.putString("user",user)
                editor.putString("pass",pass)
                editor.apply()

                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)

                etUsuario.text.clear()
                etPass.text.clear()


            }
            else if (etUsuario.getText().toString().equals(validUser) && etPass.getText().toString().equals(validPass)){

                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)

                etUsuario.text.clear()
                etPass.text.clear()

            }
            else {
                Toast.makeText(activity, "Usuario y/o Contraseña incorrectos!!", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(activity, "Usuario y contraseña no deben quedar vacios!!", Toast.LENGTH_SHORT).show()
        }


    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.menuBorrar)?.isVisible=false
        menu.findItem(R.id.menuGuardar)?.isVisible=false
        menu.findItem(R.id.menuEditar)?.isVisible=false
        menu.findItem(R.id.menInsertar)?.isVisible=false
    }

}


