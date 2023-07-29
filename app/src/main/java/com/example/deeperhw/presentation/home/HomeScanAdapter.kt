package com.example.deeperhw.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.deeperhw.R
import com.example.deeperhw.databinding.ItemScanBinding
import com.example.deeperhw.domain.model.Scan
import com.example.deeperhw.presentation.util.extensions.toDisplayValue

class HomeScanAdapter(
    private val scannedLocations: List<Scan>,
    private val onItemClick: (Int?) -> Unit
) : RecyclerView.Adapter<HomeScanAdapter.ScanViewHolder>() {
    private var actualList = emptyList<Scan>()

    init {
        actualList = scannedLocations
    }

    inner class ScanViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemScanBinding.bind(itemView)

        fun bind(data: Scan) {
            data.date?.let { binding.date.text = it.toDisplayValue() }
            binding.title.text = data.name
            binding.item.setOnClickListener { onItemClick(data.id) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeScanAdapter.ScanViewHolder =
        ScanViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_scan, parent, false))

    override fun onBindViewHolder(holder: ScanViewHolder, position: Int) {
        holder.bind(actualList[position])
    }

    override fun getItemCount(): Int = actualList.size

    fun updateList(newList: List<Scan>) {
        actualList = newList
        notifyDataSetChanged()
    }
}