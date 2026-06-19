package com.conectatarot.app

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.conectatarot.app.network.TarotistaAdmin

class TarotistaAdminViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private val tvNombre =
        itemView.findViewById<TextView>(R.id.tvNombreProfesional)

    private val tvEstado =
        itemView.findViewById<TextView>(R.id.tvEstado)

    private val btnAprobar =
        itemView.findViewById<Button>(R.id.btnAprobar)

    fun bind(
        tarotista: TarotistaAdmin,
        onAprobar: (Int) -> Unit
    ) {

        tvNombre.text =
            tarotista.nombreProfesional

        tvEstado.text =
            tarotista.estado

        btnAprobar.setOnClickListener {

            tarotista.id?.let {
                onAprobar(it)
            }

        }
    }
}