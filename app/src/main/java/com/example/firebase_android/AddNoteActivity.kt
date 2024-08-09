package com.example.firebase_android

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddNoteActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        // Inicializa la referencia de la base de datos
        database = FirebaseDatabase.getInstance().reference

        val titleEditText: EditText = findViewById(R.id.titleEditText)
        val contentEditText: EditText = findViewById(R.id.contentEditText)
        val saveButton: Button = findViewById(R.id.saveButton)

        saveButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val content = contentEditText.text.toString()

            if (title.isNotEmpty() && content.isNotEmpty()) {
                // Guarda la nota en Firebase
                val noteId = database.push().key // Genera una clave única
                val note = Note(noteId!!, title, content)

                database.child("notes").child(noteId).setValue(note)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Note saved.", Toast.LENGTH_SHORT).show()
                            finish() // Cierra la actividad
                        } else {
                            Toast.makeText(this, "Failed to save note.", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Please enter title and content.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

data class Note(
    val id: String = "",
    val title: String = "",
    val content: String = ""
) {
    // Constructor sin argumentos necesario para Firebase
    constructor() : this("", "", "")
}

