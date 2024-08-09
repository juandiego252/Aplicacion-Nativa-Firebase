package com.example.firebase_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class NotesListActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var notesRecyclerView: RecyclerView
    private lateinit var notesList: MutableList<Note>
    private lateinit var adapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_list)

        database = FirebaseDatabase.getInstance().reference.child("notes")
        notesRecyclerView = findViewById(R.id.notesRecyclerView)
        notesRecyclerView.layoutManager = LinearLayoutManager(this)
        notesList = mutableListOf()

        adapter = NotesAdapter(notesList)
        notesRecyclerView.adapter = adapter

        // Carga las notas desde Firebase
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                notesList.clear()
                for (noteSnapshot in snapshot.children) {
                    val note = noteSnapshot.getValue(Note::class.java)
                    note?.let { notesList.add(it) }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Maneja el error
            }
        })
    }
}
