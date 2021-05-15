package com.example.finaldi


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

private const val DATABASE = "recetas"

@Database(
    entities = [RoomReceta::class],
    version = 1, // siempre la version
    exportSchema = false
)
abstract class RoomRecetaDatabase : RoomDatabase() {

    abstract fun miDAO(): RecetaDAO

    companion object { // se utiliza el comp obj para que lo que este dentro se pueda instanciar solo una vez
        @Volatile
        private var INSTANCE: RoomRecetaDatabase? = null

        fun getRoomRecetaDatabase(context: Context): RoomRecetaDatabase {

            return INSTANCE ?: synchronized(this){ // si instance es null crea una instance de la room database
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomRecetaDatabase::class.java,
                    "receta_database"
                ).build()
                INSTANCE =instance
                instance
            }

        }
        //en todos los sitios que he mirado hacen el build de la data base
        private fun buildRoomRecetaDatabase(context: Context): RoomRecetaDatabase {
            return Room.databaseBuilder(context, RoomRecetaDatabase::class.java, DATABASE)
                .build()
        }



    }

}