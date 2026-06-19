package com.conectatarot.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.conectatarot.app.network.UsuarioAdmin


class UsuarioAdapter(
    private val usuarios: List<UsuarioAdmin>
) : RecyclerView.Adapter<UsuarioAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val nombre =
            view.findViewById<TextView>(R.id.tvNombre)

        val email =
            view.findViewById<TextView>(R.id.tvEmail)

        val rol =
            view.findViewById<TextView>(R.id.tvRol)
    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view =
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_usuario,
                    parent,
                    false
                )

        return ViewHolder(view)
    }



    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val usuario = usuarios[position]

        holder.nombre.text =
            usuario.nombre

        holder.email.text =
            usuario.email

        holder.rol.text =
            usuario.rol?.nombreRol ?: ""

    }



    override fun getItemCount(): Int {
        return usuarios.size
    }

}