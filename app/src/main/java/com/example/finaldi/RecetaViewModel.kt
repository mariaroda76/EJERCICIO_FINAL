package com.example.finaldi

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class RecetaViewModel (private val receRepo: RecetaRepositorio): ViewModel() { //cuando quiero que al view model le llegue un repositorio o algo, en este caso el repositorio debo hacer un ViewModelFactory ver abajo

    val listaRecetas: LiveData<List<RoomReceta>> = receRepo.listaRecetas.asLiveData() // cuando necesite que observe y me devuelva la lista
    lateinit var miReceta: LiveData<RoomReceta>// cuando necesito que me devuelva un juego haya algo observandolo y me lo devuelva

    fun Insertar (miReceta: RoomReceta) = viewModelScope.launch {
        receRepo.Insertar(miReceta)
    } // para lanzar la consulta la hago desde un hilo secundario, si quitamos el scope, no LANZARIA de una corrutina Y NO ME DEJARIA ME DARIA ERROR (ESTO ES POR LOS SUSPEND, ESTAMOS EN CORRUTINAS)

    fun BuscarPorId(id:Int)=viewModelScope.launch {
        miReceta=receRepo.BuscarPorId(id).asLiveData() //SI PUSIERA UN RETURN LLEGARIA UN FLOW, CUANDO EL DATO LLEGA, CAMBIA EL LIVE DATA Y HACE LO QUE DEBE. NECESITO EL LIVEDATA PARA ESPERAR EL DATO (LO QUE ME DEVUELVEN LOS HILOS SECUNDARIOS NO SON INSTANTANEOS, POR ESO LIVE DATA "ESPERA QUE LLEGUE EL DATO" Y CUANDO RECIBE EL CAMBIO LO GUARDA//ESPERA QUE EL HILO SECUNDARIO TERMINE
    }

    fun Borrar(miReceta: RoomReceta)=viewModelScope.launch {
        receRepo.Borrar(miReceta)
    }

    fun Actualizar(miReceta: RoomReceta) = viewModelScope.launch {
        receRepo.Actualizar(miReceta)
    }

}

class RecetaViewModelFactory(private val repository: RecetaRepositorio) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecetaViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return RecetaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}