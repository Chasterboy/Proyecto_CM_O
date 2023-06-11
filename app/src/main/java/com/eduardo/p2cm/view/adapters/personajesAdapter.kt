package com.eduardo.p2cm.view.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eduardo.p2cm.databinding.PersonajesElementBinding
import com.eduardo.p2cm.model.personajes_hp
import com.bumptech.glide.Glide
import com.eduardo.p2cm.R


class personajesAdapter (private var context: Context, private var personajesHP: ArrayList<personajes_hp>, private val clickListener: (personajes_hp) -> Unit): RecyclerView.Adapter<personajesAdapter.ViewHolder>() {

    class ViewHolder(view: PersonajesElementBinding): RecyclerView.ViewHolder(view.root){
        val ivThumbnail = view.ivThumbnail
        val nombre = view.idNombre
        val actor = view.idActor
        val genero = view.idGenero
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PersonajesElementBinding.inflate(LayoutInflater.from(context))
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = personajesHP.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nombre.text = personajesHP[position].name
        holder.actor.text = personajesHP[position].actor
        holder.genero.text = personajesHP[position].gender

        Glide.with(context)
            .load(personajesHP[position].image)
            .into(holder.ivThumbnail)

        //holder.tvDeveloper.text = "EA Sports"

        holder.itemView.setOnClickListener {
            //Para programar los eventos click al elemento completo del ViewHolder
            clickListener(personajesHP[position])
        }
    }

}
