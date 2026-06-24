package com.conectatarot.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.conectatarot.app.network.PagoAdmin


class PagoAdminAdapter(
    private val pagos: List<PagoAdmin>
) : RecyclerView.Adapter<PagoAdminAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val id = view.findViewById<TextView>(R.id.tvId)
        val cliente = view.findViewById<TextView>(R.id.tvCliente)
        val tarotista = view.findViewById<TextView>(R.id.tvTarotista)
        val monto = view.findViewById<TextView>(R.id.tvMonto)
        val estado = view.findViewById<TextView>(R.id.tvEstado)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pago_admin, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val pago = pagos[position]

        holder.id.text = "ID pago: ${pago.id ?: "-"}"
        holder.cliente.text = "Cliente: ${pago.nombreCliente ?: "-"}"
        holder.tarotista.text = "Tarotista: ${pago.nombreTarotista ?: "-"}"

        val montoTexto = pago.monto?.let { "$${"%.2f".format(it)}" } ?: "-"
        holder.monto.text = "Monto: $montoTexto"

        holder.estado.text = "Estado: ${pago.estadoPago ?: "-"}"
    }

    override fun getItemCount(): Int {
        return pagos.size
    }
}
