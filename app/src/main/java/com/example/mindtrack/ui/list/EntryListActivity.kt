package com.example.mindtrack.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mindtrack.R
import com.example.mindtrack.databinding.ActivityEntryListBinding
import com.example.mindtrack.di.Graph
import com.example.mindtrack.ui.detail.EntryDetailActivity
import com.example.mindtrack.ui.edit.AddEditEntryActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EntryListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEntryListBinding
    private lateinit var viewModel: EntryListViewModel
    private lateinit var emptyView: View
    private val adapter = EntryAdapter { id ->
        startActivity(Intent(this, EntryDetailActivity::class.java).putExtra("ENTRY_ID", id))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEntryListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        emptyView = findViewById(R.id.empty_state)

        emptyView = findViewById(R.id.empty_state)

        setSupportActionBar(binding.toolbar)

        viewModel = ViewModelProvider(this, EntryListVMFactory(Graph.repository))
            .get(EntryListViewModel::class.java)

        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter

        binding.fab.setOnClickListener {
            startActivity(Intent(this, AddEditEntryActivity::class.java))
        }

        lifecycleScope.launch {
            viewModel.entries.collectLatest { list ->
                adapter.submitList(list)
                emptyView.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val sv = searchItem.actionView as SearchView
        sv.queryHint = getString(R.string.search_hint)
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.setQuery(query.orEmpty())
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.setQuery(newText.orEmpty())
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                startActivity(Intent(this, AddEditEntryActivity::class.java)); true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
