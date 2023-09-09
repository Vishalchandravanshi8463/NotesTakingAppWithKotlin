package com.example.notesappkotlin

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.notesappkotlin.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    lateinit var btnAddNotes:Button
    lateinit var fabNotes:FloatingActionButton
    lateinit var recyclerViewNotes:RecyclerView
    private lateinit var llNoNotes:LinearLayout
    private lateinit var databaseHelper:DatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)


        btnAddNotes=binding.btnAddNotes
        fabNotes=binding.fabNotes
        recyclerViewNotes=binding.recyclerViewNotes
        llNoNotes=binding.llNoNotes
        databaseHelper= DatabaseHelper.getInstance(this)
        recyclerViewNotes.layoutManager = GridLayoutManager(this, 2)

        showNotes()


        fabNotes.setOnClickListener {

            val dialog=Dialog(this@MainActivity)
            dialog.setContentView(R.layout.add_notes)


            val edtTittlee:EditText
            val edtDescriptionn:EditText
            val btnAddd:Button


            edtDescriptionn=dialog.findViewById(R.id.edtDescription)
            btnAddd=dialog.findViewById(R.id.btnAdd)
            edtTittlee=dialog.findViewById(R.id.edtTittle)

            btnAddd.setOnClickListener {

                val tittle=edtTittlee.text.toString()
                val description=edtDescriptionn.text.toString()

                if(tittle.isEmpty())
                {
                    Toast.makeText(this, "Tittle is required", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    databaseHelper.noteDao().addNote(Note(tittle,description));
                    showNotes()
                    Toast.makeText(this, "Note save", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            }


            dialog.show()
        }
        btnAddNotes.setOnClickListener {
            fabNotes.performClick()
        }
    }
    fun showNotes()
    {
        val arrNotes= databaseHelper.noteDao().getNotes()

        if(arrNotes.isNotEmpty())
        {
            recyclerViewNotes.visibility= View.VISIBLE
            llNoNotes.visibility=View.GONE

            recyclerViewNotes.adapter=RecyclerNotesAdapter(this,arrNotes as ArrayList<Note>,databaseHelper)
        }
        else
        {
            llNoNotes.visibility=View.VISIBLE
            recyclerViewNotes.visibility= View.GONE

        }
    }
}