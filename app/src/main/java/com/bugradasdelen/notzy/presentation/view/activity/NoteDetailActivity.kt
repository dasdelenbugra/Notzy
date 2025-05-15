package com.bugradasdelen.notzy.presentation.view.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bugradasdelen.notzy.R
import com.bugradasdelen.notzy.databinding.ActivityNoteDetailBinding
import com.bugradasdelen.notzy.domain.model.Note
import com.bugradasdelen.notzy.presentation.viewmodel.NoteViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.Date

class NoteDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteDetailBinding
    private lateinit var noteViewModel: NoteViewModel
    private var currentNoteId: Int = 0
    private var isNewNote: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.edit_note)

        noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        // Check if we're editing an existing note
        currentNoteId = intent.getIntExtra("note_id", 0)
        isNewNote = currentNoteId == 0

        if (!isNewNote) {
            loadNote(currentNoteId)
        }

        val recognizedTxt = intent.getStringExtra("ocr_text")
        if (recognizedTxt != null) {
            binding.editTextContent.setText(recognizedTxt)
        }

        binding.toolbar.setNavigationOnClickListener {
            saveNote()
            finish()
        }
    }

    private fun loadNote(noteId: Int) {
        noteViewModel.getNoteById(noteId).observe(this) { note ->
            note?.let {
                binding.editTextTitle.setText(it.title)
                binding.editTextContent.setText(it.content)
            }
        }
    }

    private fun saveNote() {
        val title = binding.editTextTitle.text.toString().trim()
        val content = binding.editTextContent.text.toString().trim()

        if (title.isEmpty() && content.isEmpty()) {
            finish()
            return
        }

        val finalTitle = if (title.isEmpty()) getString(R.string.untitled) else title

        if (isNewNote) {
            val newNote = Note(
                title = finalTitle,
                content = content
            )
            noteViewModel.insert(newNote)
        } else {
            val updatedNote = Note(
                id = currentNoteId,
                title = finalTitle,
                content = content,
                updatedAt = Date()
            )
            noteViewModel.update(updatedNote)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_note_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete -> {
                showDeleteConfirmationDialog()
                true
            }

            android.R.id.home -> {
                saveNote()
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDeleteConfirmationDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.delete_note))
            .setMessage(getString(R.string.delete_note_message))
            .setPositiveButton(getString(R.string.delete)) { _, _ ->
                if (!isNewNote) {
                    noteViewModel.getNoteById(currentNoteId).observe(this) { note ->
                        note?.let {
                            noteViewModel.delete(note)
                            finish()
                        }
                    }
                } else {
                    finish()
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    override fun onBackPressed() {
        saveNote()
        super.onBackPressed()
    }
}