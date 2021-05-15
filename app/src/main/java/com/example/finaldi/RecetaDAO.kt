package com.example.finaldi

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface RecetaDAO {

    @Query("SELECT * FROM recetas ORDER BY recetaId ASC")
    fun getRecetas(): Flow<List<RoomReceta>> //que es el flow??

    //@Insert(onConflict = OnConflictStrategy.REPLACE)
    //suspend fun insertOrUpdatePelicula(pelicula: RoomReceta)

   @Insert(onConflict = OnConflictStrategy.IGNORE)
   suspend fun insertReceta(receta: RoomReceta)// suspend solo se puede ejecutar de una corutina, no se puede ejecuutar desde el hilo ppal, si devuelve algo, el dato será de tipo flow, lo ira devolviendo podo a poco. si devuelve un flow, sera por narices una corutina

    @Query ("DELETE FROM recetas")
    suspend fun borrarTodas() // no devuelve suspend

    @Query("SELECT * FROM recetas WHERE recetaId like :id")
    fun getrecetaById(id: Int): Flow<RoomReceta> //que es el flow?? // como devuelve tiene flow

    @Update
    suspend fun actualizar(receta : RoomReceta)

    @Delete
    suspend fun borrarReceta(receta: RoomReceta)


}