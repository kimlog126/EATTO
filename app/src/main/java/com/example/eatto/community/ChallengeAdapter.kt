package com.example.eatto.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eatto.R

class ChallengeAdapter(private val items: List<ChallengeItem>) :
    RecyclerView.Adapter<ChallengeAdapter.ChallengeViewHolder>() {

    inner class ChallengeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tag: TextView = view.findViewById(R.id.tag)
        val title: TextView = view.findViewById(R.id.title)
        val date: TextView = view.findViewById(R.id.date)
        val participation: TextView = view.findViewById(R.id.participation)
        val point: TextView = view.findViewById(R.id.point)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_challenge, parent, false)
        return ChallengeViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        val item = items[position]
        holder.tag.text = item.tag
        holder.title.text = item.title
        holder.date.text = item.date
        holder.participation.text = item.participation
        holder.point.text = item.point
    }

    override fun getItemCount(): Int = items.size
}
