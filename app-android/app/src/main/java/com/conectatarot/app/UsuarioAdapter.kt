package com.conectatarot.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.conectatarot.app.network.UsuarioAdmin


class UsuarioAdapter(
    private val usuarios: List<UsuarioAdmin>,
    private val onBloquear: (Int) -> Unit,
    private val onDesbloquear: (Int) -> Unit
) : RecyclerView.Adapter<UsuarioAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val nombre =
            view.findViewById<TextView>(R.id.tvNombre)

        val email =
            view.findViewById<TextView>(R.id.tvEmail)

        val rol =
            view.findViewById<TextView>(R.id.tvRol)

        val estado =
            view.findViewById<TextView>(R.id.tvEstado)

        val btnBloquear =
            view.findViewById<Button>(R.id.btnBloquear)

        val btnDesbloquear =
            view.findViewById<Button>(R.id.btnDesbloquear)
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

        val activo = usuario.activo != false

        holder.estado.text =
            if (activo) "Estado: Activo" else "Estado: Bloqueado"

        holder.btnBloquear.visibility =
            if (activo) View.VISIBLE else View.GONE

        holder.btnDesbloquear.visibility =
            if (activo) View.GONE else View.VISIBLE

        val id = usuario.idUsuario ?: return

        holder.btnBloquear.setOnClickListener {
            onBloquear(id)
        }

        holder.btnDesbloquear.setOnClickListener {
            onDesbloquear(id)
        }
    }



    override fun getItemCount(): Int {
        return usuarios.size
    }

}
