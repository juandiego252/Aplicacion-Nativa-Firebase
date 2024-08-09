package com.example.firebase_android

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        val addNoteButton: Button = findViewById(R.id.addNoteButton)
        val viewNotesButton: Button = findViewById(R.id.viewNotesButton)
        val logoutButton: Button = findViewById(R.id.logoutButton)  // Referencia al botón de cerrar sesión

        addNoteButton.setOnClickListener {
            startActivity(Intent(this, AddNoteActivity::class.java))
        }

        viewNotesButton.setOnClickListener {
            startActivity(Intent(this, NotesListActivity::class.java))
        }

        logoutButton.setOnClickListener {
            auth.signOut()  // Cierra la sesión del usuario
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()  // Finaliza la MainActivity para que no quede en la pila de actividades
        }
    }
}
