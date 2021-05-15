package com.example.finaldi

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

class Adaptador(var listaRecetas: List<RoomReceta>, val actividad:Activity) :
    RecyclerView.Adapter<Adaptador.ViewHolder>() {

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        var tvTituloItem: TextView
        var tvCategoriaItem:TextView
        var tvDificultadItem:TextView
        var tvTiempoItem:TextView

        var id:Int

        init{

            tvTituloItem=v.findViewById(R.id.tv_item_titulo)
            tvCategoriaItem=v.findViewById(R.id.tv_item_categoria)
            tvDificultadItem=v.findViewById(R.id.v_item_dificu)
            tvTiempoItem=v.findViewById(R.id.tv_item_tiempo)

            id=0
            v.setOnClickListener{
                val bundle = bundleOf("id" to this.id)
                (actividad as MainActivity).findNavController(R.id.nav_host_fragment).navigate(R.id.action_SecondFragment_to_detailRecetaFragment,bundle)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.item, parent, false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvTituloItem.text=listaRecetas[position].titulo
        holder.tvCategoriaItem.text=listaRecetas[position].categoria
        holder.tvDificultadItem.text=listaRecetas[position].dificultad.toString()
        holder.tvTiempoItem.text=listaRecetas[position].tiempo.toString()
/*        holder.tvIngredientesItem.text=listaRecetas[position].ingredientes
        holder.tvPasosItem.text=listaRecetas[position].pasos*/
        holder.id=listaRecetas[position].recetaId
    }

    override fun getItemCount(): Int {
        return listaRecetas.count()
    }

}