package com.bugradasdelen.notzy.presentation.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bugradasdelen.notzy.R
import com.bugradasdelen.notzy.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.settings)

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        // Settings UI and logic would be implemented here
    }
}