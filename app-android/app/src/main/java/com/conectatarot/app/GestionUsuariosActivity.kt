package com.conectatarot.app

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.conectatarot.app.network.RetrofitClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class GestionUsuariosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_gestion_usuarios)

        val prefs =
            getSharedPreferences(
                "conectatarot",
                MODE_PRIVATE
            )

        val token =
            prefs.getString(
                "token",
                null
            )

        if (token.isNullOrEmpty()) {

            val intent =
                Intent(
                    this,
                    MainActivity::class.java
                )

            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)

            finish()

            return
        }

        val tvLogout =
            findViewById<TextView>(R.id.tvLogout)

        tvLogout.setOnClickListener {

            prefs.edit().clear().apply()

            val intent =
                Intent(
                    this,
                    MainActivity::class.java
                )

            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)

            finish()
        }

        val bottomNav =
            findViewById<BottomNavigationView>(R.id.bottomNav)

        bottomNav.selectedItemId = R.id.nav_usuarios

        bottomNav.setOnItemSelectedListener { item ->

            when (item.itemId) {

                R.id.nav_dashboard -> {

                    startActivity(
                        Intent(
                            this,
                            AdminActivity::class.java
                        )
                    )

                    true
                }

                R.id.nav_tarotistas -> {

                    startActivity(
                        Intent(
                            this,
                            GestionTarotistasActivity::class.java
                        )
                    )

                    true
                }

                else -> true
            }
        }

        val rvUsuarios =
            findViewById<RecyclerView>(R.id.rvUsuarios)

        rvUsuarios.layoutManager =
            LinearLayoutManager(this)

        lifecycleScope.launch {

            try {

                val response =
                    RetrofitClient.instance
                        .getAdminUsuarios(
                            "Bearer $token"
                        )

                if (response.isSuccessful) {

                    val usuarios =
                        response.body()?.data ?: emptyList()

                    rvUsuarios.adapter =
                        UsuarioAdapter(usuarios)

                }

            } catch (e: Exception) {

                Toast.makeText(
                    this@GestionUsuariosActivity,
                    "Error de conexión",
                    Toast.LENGTH_LONG
                ).show()

            }

        }
    }
}