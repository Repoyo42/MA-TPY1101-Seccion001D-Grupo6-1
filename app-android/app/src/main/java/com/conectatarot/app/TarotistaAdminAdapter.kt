package com.conectatarot.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.conectatarot.app.network.TarotistaAdmin

class TarotistaAdminAdapter(
    private val tarotistas: List<TarotistaAdmin>,
    private val onAprobar: (Int) -> Unit
) : RecyclerView.Adapter<TarotistaAdminViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TarotistaAdminViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_tarotista_admin,
                parent,
                false
            )

        return TarotistaAdminViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: TarotistaAdminViewHolder,
        position: Int
    ) {

        val tarotista = tarotistas[position]

        holder.bind(
            tarotista,
            onAprobar
        )
    }

    override fun getItemCount(): Int {
        return tarotistas.size
    }
}