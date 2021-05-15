package com.example.finaldi


import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "recetas"
)

data class RoomReceta(


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "recetaId")
    val recetaId: Int = 0,

    @ColumnInfo(name = "titulo")
    @NonNull
    val titulo: String,

    @ColumnInfo(name = "categoria")
    @NonNull
    val categoria: String,

    @ColumnInfo(name = "dificultad")
    @NonNull
    val dificultad: Int,

    @ColumnInfo(name = "tiempo")
    @NonNull
    val tiempo: Int,

    @ColumnInfo(name = "ingredientes")
    @NonNull
    val ingredientes: String,

    @ColumnInfo(name = "pasos")
    @NonNull
    val pasos: String

)


