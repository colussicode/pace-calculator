package com.example.myapplication.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.Pace

class PaceAdapter : ListAdapter<Pace, PaceViewHolder>(PaceAdapter) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaceViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_pace, parent, false)

        return PaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PaceViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object : DiffUtil.ItemCallback<Pace>() {
        override fun areItemsTheSame(oldItem: Pace, newItem: Pace): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Pace, newItem: Pace): Boolean {
            return oldItem.pace == newItem.pace && oldItem.time == newItem.time
        }

    }
}

class PaceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val textViewKm : TextView = view.findViewById(R.id.textview_km)
    private val textViewTempo : TextView = view.findViewById(R.id.textview_result_time)
    private val textViewPace : TextView = view.findViewById(R.id.textview_result_pace)

    fun bind(pace: Pace) {
        textViewKm.text = pace.distance
        textViewTempo.text = pace.time
        textViewPace.text = pace.pace
    }
}