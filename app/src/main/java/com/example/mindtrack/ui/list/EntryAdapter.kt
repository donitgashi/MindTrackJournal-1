package com.example.mindtrack.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mindtrack.data.local.Entry
import com.example.mindtrack.databinding.ItemEntryBinding
import java.text.SimpleDateFormat
import java.util.*

class EntryAdapter(private val onClick: (Long) -> Unit) :
    ListAdapter<Entry, EntryAdapter.VH>(Diff) {

    object Diff : DiffUtil.ItemCallback<Entry>() {
        override fun areItemsTheSame(a: Entry, b: Entry) = a.id == b.id
        override fun areContentsTheSame(a: Entry, b: Entry) = a == b
    }

    inner class VH(val binding: ItemEntryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemEntryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val e = getItem(position)
        holder.binding.title.text = e.title
        holder.binding.tags.text = e.tagsCsv
        holder.binding.mood.text = "Mood: ${e.mood}"
        val date = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date(e.createdAt))
        holder.binding.date.text = date
        if (e.photoUri != null) {
            holder.binding.thumbnail.setImageURI(android.net.Uri.parse(e.photoUri))
        } else {
            holder.binding.thumbnail.setImageResource(android.R.color.transparent)
        }
        holder.binding.root.setOnClickListener { onClick(e.id) }
    }
}