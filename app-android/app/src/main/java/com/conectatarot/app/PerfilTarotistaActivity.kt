package com.conectatarot.app

import android.os.Bundle
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.conectatarot.app.network.EditarPerfilTarotistaRequest
import com.conectatarot.app.network.RetrofitClient
import kotlinx.coroutines.launch

class PerfilTarotistaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_tarotista)

        val prefs = getSharedPreferences("conectatarot", MODE_PRIVATE)
        val token = prefs.getString("token", "") ?: ""
        val idUsuario = prefs.getInt("idUsuario", 0)

        val etNombrePro = findViewById<EditText>(R.id.etEditNombrePro)
        val etDescripcion = findViewById<EditText>(R.id.etEditDescripcion)
        val etPrecio = findViewById<EditText>(R.id.etEditPrecio)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarPerfilTarotista)
        val tvResultado = findViewById<TextView>(R.id.tvResultadoPerfilTarotista)
        val tvVolver = findViewById<TextView>(R.id.tvVolverPerfilTarotista)
        val btnLogout = findViewById<Button>(R.id.btnLogoutTarotista)
        val btnEliminar = findViewById<Button>(R.id.btnEliminarCuentaTarotista)

        // Cargar datos guardados
        etNombrePro.setText(prefs.getString("nombreProfesional", "") ?: "")
        etDescripcion.setText(prefs.getString("descripcion", "") ?: "")
        etPrecio.setText(prefs.getString("precioBase", "") ?: "")

        tvVolver.setOnClickListener { finish() }

        btnLogout.setOnClickListener {
            prefs.edit().clear().apply()
            val intent = android.content.Intent(this, MainActivity::class.java)
            intent.flags = android.content.Intent.FLAG_ACTIVITY_NEW_TASK or android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        btnEliminar.setOnClickListener {
            mostrarDialogoEliminar(token, idUsuario, prefs)
        }

        btnGuardar.setOnClickListener {
            val nombrePro = etNombrePro.text.toString().trim()
            val descripcion = etDescripcion.text.toString().trim()
            val precioStr = etPrecio.text.toString().trim()

            if (nombrePro.isEmpty() || descripcion.isEmpty() || precioStr.isEmpty()) {
                tvResultado.text = "Por favor completa todos los campos"
                tvResultado.setTextColor(getColor(android.R.color.holo_red_light))
                return@setOnClickListener
            }

            val precio = precioStr.toDoubleOrNull() ?: run {
                tvResultado.text = "El precio debe ser un número válido"
                tvResultado.setTextColor(getColor(android.R.color.holo_red_light))
                return@setOnClickListener
            }

            btnGuardar.isEnabled = false
            btnGuardar.text = "Guardando..."

            lifecycleScope.launch {
                try {
                    val response = RetrofitClient.instance.editarPerfilTarotista(
                        "Bearer $token",
                        idUsuario,
                        EditarPerfilTarotistaRequest(nombrePro, descripcion, precio)
                    )
                    if (response.isSuccessful) {
                        prefs.edit()
                            .putString("nombreProfesional", nombrePro)
                            .putString("descripcion", descripcion)
                            .putString("precioBase", precioStr)
                            .apply()
                        tvResultado.text = "✅ Perfil actualizado correctamente"
                        tvResultado.setTextColor(getColor(android.R.color.holo_green_light))
                        btnGuardar.text = "Guardado ✓"
                    } else {
                        tvResultado.text = "❌ Error al actualizar"
                        tvResultado.setTextColor(getColor(android.R.color.holo_red_light))
                        btnGuardar.isEnabled = true
                        btnGuardar.text = "Guardar cambios"
                    }
                } catch (e: Exception) {
                    tvResultado.text = "❌ Error de conexión"
                    tvResultado.setTextColor(getColor(android.R.color.holo_red_light))
                    btnGuardar.isEnabled = true
                    btnGuardar.text = "Guardar cambios"
                }
            }
        }
    }

    private fun mostrarDialogoEliminar(token: String, idUsuario: Int, prefs: android.content.SharedPreferences) {
        val input = EditText(this)
        input.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
        input.hint = "Ingresa tu contraseña"

        val container = FrameLayout(this)
        val params = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.leftMargin = 50
        params.rightMargin = 50
        input.layoutParams = params
        container.addView(input)

        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Eliminar cuenta")
            .setMessage("¿Estás seguro? Esta acción no se puede deshacer. Por favor confirma tu contraseña.")
            .setView(container)
            .setPositiveButton("Eliminar") { _, _ ->
                val password = input.text.toString()
                if (password.isNotEmpty()) {
                    eliminarCuentaProceso(token, idUsuario, password, prefs)
                } else {
                    Toast.makeText(this, "Debes ingresar tu contraseña", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun eliminarCuentaProceso(token: String, idUsuario: Int, password: String, prefs: android.content.SharedPreferences) {
        lifecycleScope.launch {
            try {
                // Nota: se usa el idUsuario real. En el código original decía val idUsuario = 1 (fijo).
                // He mantenido esa coherencia o podrías querer obtenerlo de prefs.getInt("idUsuario", 0)
                val realId = prefs.getInt("idUsuario", idUsuario) 

                val response = RetrofitClient.instance.eliminarCuenta(
                    "Bearer $token",
                    realId,
                    com.conectatarot.app.network.EliminarCuentaRequest(password)
                )
                if (response.isSuccessful) {
                    Toast.makeText(this@PerfilTarotistaActivity, "Cuenta eliminada", Toast.LENGTH_LONG).show()
                    prefs.edit().clear().apply()
                    val intent = android.content.Intent(this@PerfilTarotistaActivity, MainActivity::class.java)
                    intent.flags = android.content.Intent.FLAG_ACTIVITY_NEW_TASK or android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                } else {
                    val errorMsg = when(response.code()) {
                        401 -> "Contraseña incorrecta"
                        404 -> "Endpoint no encontrado en el servidor"
                        500 -> "Error interno del servidor (500)"
                        else -> "Error: ${response.code()}"
                    }
                    Toast.makeText(this@PerfilTarotistaActivity, errorMsg, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@PerfilTarotistaActivity, "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        }
    }
}