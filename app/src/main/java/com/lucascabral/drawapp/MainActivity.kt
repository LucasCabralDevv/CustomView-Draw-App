package com.lucascabral.drawapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lucascabral.drawapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listenerToClick()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflate: MenuInflater = menuInflater
        inflate.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuDeleteDrawing -> { showAlertDialogDeleteDrawing() }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun listenerToClick() {
        binding.undoImageView.setOnClickListener {
            binding.drawPath.undoPath()
        }

        binding.redoImageView.setOnClickListener {
            binding.drawPath.redoPath()
        }
    }

    private fun showAlertDialogDeleteDrawing() {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.alert_dialog_title_text)
            .setMessage(R.string.alert_dialog_message_text)
            .setNegativeButton(getString(R.string.alert_dialog_negative)) { _, _ ->
                //todo
            }
            .setPositiveButton(getString(R.string.alert_dialog_positive)) { _, _ ->
                binding.drawPath.deleteDrawing()
            }
            .setCancelable(false)
            .show()
    }
}