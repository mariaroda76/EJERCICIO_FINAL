package com.example.finaldi

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class RecetaRepositorio (val miDAO: RecetaDAO) { //si tuviese otra bd le paso otra variable dao aqui y abajo los implemento

    val listaRecetas: Flow<List<RoomReceta>> = miDAO.getRecetas()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun Insertar (nuevaReceta: RoomReceta){ // no devuelve nada entonces suspend
        miDAO.insertReceta(nuevaReceta)
    }

    fun BuscarPorId(id:Int): Flow<RoomReceta>{
        return miDAO.getrecetaById(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun Borrar (oldReceta: RoomReceta){
        miDAO.borrarReceta(oldReceta)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun Actualizar (oldReceta: RoomReceta){
        miDAO.actualizar(oldReceta)
    }

    //falta borrar todas???


}