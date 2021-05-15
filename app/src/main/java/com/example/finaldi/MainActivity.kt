package com.example.finaldi

import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    //val myViewModel:VM by viewModels()

    //SI EN LA APP HUBIESE MAS DE UNA ACTIVIDAD DEBERIA HACER ESTO DE LAS VARIABLES EN LA CLASE APP, COMO ESTOY CON FRAGMENTOS, LO HAGO EN EL MAINACTIVITY
    //DEBO CREAR UNA CLASE APP Y AGREGARLA EN EL MANIFEST

    //'VARIABLES'

    val database by lazy { RoomRecetaDatabase.getRoomRecetaDatabase(this)}// BY LAZY NO SE CREA LA INSTANCIA DE LA BDD HASTA QUE NO SE USE POR PRIMERA VEZ
    val miRepositorio by lazy { RecetaRepositorio(database.miDAO()) }
    val myViewModel: RecetaViewModel by viewModels {RecetaViewModelFactory(miRepositorio)}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }



}