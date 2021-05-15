package com.example.finaldi

import androidx.lifecycle.ViewModel

class VM : ViewModel() {

    val listaPeliculas: MutableList<Pelicula> = mutableListOf()

    init {
        cargarPeliculas()
    }


    fun cargarPeliculas() {

        listaPeliculas.add(Pelicula("Pelicula 1", "Humor", 2020))
        listaPeliculas.add(Pelicula("Pelicula 2", "Humor", 1984))
        listaPeliculas.add(Pelicula("Pelicula 3", "Humor", 1999))
        listaPeliculas.add(Pelicula("Pelicula 4", "Humor", 2021))
        listaPeliculas.add(Pelicula("Pelicula 5", "Humor", 1958))
        listaPeliculas.add(Pelicula("Pelicula 6", "Humor", 1932))
        listaPeliculas.add(Pelicula("Pelicula 7", "Humor", 1995))
        listaPeliculas.add(Pelicula("Pelicula 8", "Humor", 2004))
        listaPeliculas.add(Pelicula("Pelicula 9", "Humor", 1987))
        listaPeliculas.add(Pelicula("Pelicula 10", "Humor", 1976))
        listaPeliculas.add(Pelicula("Pelicula 11", "Humor", 1952))
        listaPeliculas.add(Pelicula("Pelicula 12", "Humor", 1984))
        listaPeliculas.add(Pelicula("Pelicula 13", "Humor", 1977))
        listaPeliculas.add(Pelicula("Pelicula 14", "Humor", 1947))
        listaPeliculas.add(Pelicula("Pelicula 15", "Humor", 2019))


    }

    fun insertar(pelicula: Pelicula) {
        listaPeliculas.add(pelicula)
    }

    fun borrar(posicion: Int) {
        listaPeliculas.removeAt(posicion)
    }

    fun modificar(pelicula: Pelicula, posicion: Int) {
        listaPeliculas[posicion].titulo = pelicula.titulo
        listaPeliculas[posicion].genero = pelicula.genero
        listaPeliculas[posicion].anyoEstreno = pelicula.anyoEstreno

    }


}