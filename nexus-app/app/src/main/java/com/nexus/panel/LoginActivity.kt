package com.nexus.panel

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etUser  = findViewById<EditText>(R.id.etUsername)
        val etPass  = findViewById<EditText>(R.id.etPassword)
        val btnLogin= findViewById<Button>(R.id.btnLogin)
        val tvForgot= findViewById<TextView>(R.id.tvForgot)

        btnLogin.setOnClickListener {
            val user = etUser.text.toString().trim()
            val pass = etPass.text.toString().trim()
            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // qualquer user/pass passa — Sam conecta auth real depois
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        tvForgot.setOnClickListener {
            Toast.makeText(this, "Contate o suporte", Toast.LENGTH_SHORT).show()
        }
    }
}
