package com.lucascabral.drawapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lucascabral.drawapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.undoImageView.setOnClickListener {
            binding.drawPath.setUndo()
        }

        binding.redoImageView.setOnClickListener {
            binding.drawPath.setRedo()
        }
    }
}