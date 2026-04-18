package com.example.memorylane2

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.memorylane2.databinding.ItemCapsuleBinding

class CapsuleAdapter(
    private val list: List<Capsule>,
    private val onItemClick: (Capsule, Int) -> Unit
) : RecyclerView.Adapter<CapsuleAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemCapsuleBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCapsuleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val capsule = list[position]

        holder.binding.title.text = capsule.title

        val sdf = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault())

        if (capsule.openDate > System.currentTimeMillis()) {
            holder.binding.date.text = "🔒 Opens on " + sdf.format(java.util.Date(capsule.openDate))
            holder.itemView.alpha = 0.5f
        } else {
            holder.binding.date.text = sdf.format(java.util.Date(capsule.openDate))
            holder.itemView.alpha = 1f
        }

        if (capsule.imageUri != null) {
            holder.binding.itemImage.setImageURI(Uri.parse(capsule.imageUri))
        } else {
            holder.binding.itemImage.setImageResource(android.R.drawable.ic_menu_gallery)
        }

        holder.itemView.setOnClickListener {
            if (capsule.openDate <= System.currentTimeMillis()) {
                onItemClick(capsule, position)
            }
        }
    }
}