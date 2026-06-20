package com.conectatarot.app

import android.os.Bundle
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.conectatarot.app.network.EditarPerfilRequest
import com.conectatarot.app.network.RetrofitClient
import kotlinx.coroutines.launch

class PerfilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        val prefs = getSharedPreferences("conectatarot", MODE_PRIVATE)
        val token = prefs.getString("token", "") ?: ""
        val idUsuario = prefs.getInt("idUsuario", 0)
        val nombreActual = prefs.getString("nombre", "") ?: ""

        val etNombre = findViewById<EditText>(R.id.etPerfilNombre)
        val etEmail = findViewById<EditText>(R.id.etPerfilEmail)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarPerfil)
        val tvResultado = findViewById<TextView>(R.id.tvResultadoPerfil)
        val tvVolver = findViewById<TextView>(R.id.tvVolverPerfil)
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        val btnEliminar = findViewById<Button>(R.id.btnEliminarCuenta)

        etNombre.setText(nombreActual)
        etEmail.setText(prefs.getString("email", "") ?: "")

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
            val nombre = etNombre.text.toString().trim()
            val email = etEmail.text.toString().trim()

            if (nombre.isEmpty() || email.isEmpty()) {
                tvResultado.text = "Por favor completa todos los campos"
                tvResultado.setTextColor(getColor(android.R.color.holo_red_light))
                return@setOnClickListener
            }

            btnGuardar.isEnabled = false
            btnGuardar.text = "Guardando..."

            lifecycleScope.launch {
                try {
                    val response = RetrofitClient.instance.editarPerfil(
                        "Bearer $token",
                        idUsuario,
                        EditarPerfilRequest(nombre, email)
                    )
                    if (response.isSuccessful) {
                        prefs.edit()
                            .putString("nombre", nombre)
                            .putString("email", email)
                            .apply()
                        tvResultado.text = "✅ Perfil actualizado correctamente"
                        tvResultado.setTextColor(getColor(android.R.color.holo_green_light))
                        btnGuardar.text = "Guardado"
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
                val response = RetrofitClient.instance.eliminarCuenta(
                    "Bearer $token",
                    idUsuario,
                    com.conectatarot.app.network.EliminarCuentaRequest(password)
                )
                if (response.isSuccessful) {
                    Toast.makeText(this@PerfilActivity, "Cuenta eliminada", Toast.LENGTH_LONG).show()
                    prefs.edit().clear().apply()
                    val intent = android.content.Intent(this@PerfilActivity, MainActivity::class.java)
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
                    Toast.makeText(this@PerfilActivity, errorMsg, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@PerfilActivity, "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        }
    }
}