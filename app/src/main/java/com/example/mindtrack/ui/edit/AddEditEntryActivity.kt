package com.example.mindtrack.ui.edit

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mindtrack.databinding.ActivityAddEditEntryBinding
import com.example.mindtrack.di.Graph

class AddEditEntryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditEntryBinding
    private lateinit var vm: AddEditEntryViewModel

    private val pickPhoto = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            vm.photoUri = uri.toString()
            binding.photo.setImageURI(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddEditEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        vm = ViewModelProvider(this, AddEditVMFactory(Graph.repository))
            .get(AddEditEntryViewModel::class.java)

        binding.mood.value = vm.mood.toFloat()

        binding.pickPhoto.setOnClickListener {
            pickPhoto.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.save.setOnClickListener {
            vm.title = binding.title.text?.toString()?.trim().orEmpty()
            vm.body = binding.body.text?.toString()?.trim().orEmpty()
            vm.mood = binding.mood.value.toInt().coerceIn(1, 5)
            vm.tagsCsv = binding.tags.text?.toString()?.trim().orEmpty()
            vm.save {
                finish()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean { finish(); return true }
}