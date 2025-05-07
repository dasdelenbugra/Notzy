package com.bugradasdelen.notzy.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bugradasdelen.notzy.R
import com.bugradasdelen.notzy.databinding.ActivityMainBinding
import com.bugradasdelen.notzy.presentation.view.adapter.NoteAdapter
import com.bugradasdelen.notzy.presentation.viewmodel.NoteViewModel
import com.bugradasdelen.notzy.utils.theme.ThemeHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.app_name)

        setupRecyclerView()
        setupViewModel()
        setupListeners()
    }

    private fun setupRecyclerView() {
        adapter = NoteAdapter { note ->
            val intent = Intent(this, NoteDetailActivity::class.java)
            intent.putExtra("note_id", note.id)
            startActivity(intent)
        }

        binding.recyclerViewNotes.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerViewNotes.adapter = adapter
    }

    private fun setupViewModel() {
        noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]
        noteViewModel.allNotes.observe(this) { notes ->
            adapter.submitList(notes)
        }
    }

    private fun setupListeners() {
        binding.fabAddNote.setOnClickListener {
            val intent = Intent(this, NoteDetailActivity::class.java)
            startActivity(intent)
        }

        binding.fabCamera.setOnClickListener {
            val intent = Intent(this, OcrCaptureActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }

            R.id.action_theme -> {
                showThemeDialog()
                true
            }

            R.id.action_language -> {
                showLanguageDialog()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showThemeDialog() {
        val themes = arrayOf(
            getString(R.string.theme_light),
            getString(R.string.theme_dark),
            getString(R.string.theme_system)
        )

        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.choose_theme))
            .setItems(themes) { _, which ->
                when (which) {
                    0 -> applyTheme("light")
                    1 -> applyTheme("dark")
                    2 -> applyTheme("default")
                }
            }
            .show()
    }

    private fun applyTheme(theme: String) {
        // Save preference and apply theme
        val sharedPrefs = getSharedPreferences("NotzyPrefs", MODE_PRIVATE)
        sharedPrefs.edit().putString("theme", theme).apply()
        ThemeHelper.applyTheme(theme)
    }

    private fun showLanguageDialog() {
        val languages = arrayOf(
            "English",
            "Türkçe"
        )

        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.choose_language))
            .setItems(languages) { _, which ->
                val languageCode = when (which) {
                    0 -> "en"
                    1 -> "tr"
                    else -> "en"
                }

                // Save preference and apply language
                val sharedPrefs = getSharedPreferences("NotzyPrefs", MODE_PRIVATE)
                sharedPrefs.edit().putString("language", languageCode).apply()

                // Restart activity to apply language changes
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            .show()
    }
}