package com.example.mindtrack.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mindtrack.databinding.ActivityEntryDetailBinding
import com.example.mindtrack.di.Graph
import com.example.mindtrack.ui.edit.AddEditEntryActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EntryDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEntryDetailBinding
    private lateinit var vm: EntryDetailViewModel
    private var id: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEntryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        id = intent.getLongExtra("ENTRY_ID", -1L)
        vm = ViewModelProvider(this, EntryDetailVMFactory(Graph.repository, id))
            .get(EntryDetailViewModel::class.java)

        lifecycleScope.launch {
            vm.entry.collectLatest { e ->
                supportActionBar?.title = e.title
                binding.title.text = e.title
                binding.body.text  = e.body
                binding.mood.text  = "Mood: ${e.mood}"
                binding.tags.text  = e.tagsCsv
                e.photoUri?.let { binding.photo.setImageURI(Uri.parse(it)) } ?: run {
                    binding.photo.setImageDrawable(null)
                }
            }
        }

        binding.fabShare.setOnClickListener {
            val txt = "${binding.title.text}\n\n${binding.body.text}\n#${binding.tags.text}"
            startActivity(Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, txt)
            })
        }
        binding.fabEdit.setOnClickListener {
            startActivity(Intent(this, AddEditEntryActivity::class.java).putExtra("ENTRY_ID", id))
        }
        binding.fabDelete.setOnClickListener {
            lifecycleScope.launch {
                Graph.repository.delete(id)
                finish()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
