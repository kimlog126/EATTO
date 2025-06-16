package com.example.eatto.community

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eatto.R

class MagazineAdapter(private val items: List<MagazineItem>) :
    RecyclerView.Adapter<MagazineAdapter.MagazineViewHolder>() {

    inner class MagazineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nickname: TextView? = itemView.findViewById(R.id.nickname2)
        val title: TextView? = itemView.findViewById(R.id.magazineTitle)
        val time: TextView? = itemView.findViewById(R.id.time2)
        val profileImage: ImageView? = itemView.findViewById(R.id.magazinePropile)
        val contentImage: ImageView? = itemView.findViewById(R.id.magazinImage)
        val likeCount: TextView? = itemView.findViewById(R.id.likeCount)
        val commentCount: TextView? = itemView.findViewById(R.id.commentCount)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MagazineViewHolder {
        Log.d("MagazineAdapter", "onCreateViewHolder called")
        try {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_magazine, parent, false)
            Log.d("MagazineAdapter", "item_magazine layout inflated successfully")
            return MagazineViewHolder(view)
        } catch (e: Exception) {
            Log.e("MagazineAdapter", "Error creating ViewHolder", e)
            throw e
        }
    }

    override fun onBindViewHolder(holder: MagazineViewHolder, position: Int) {
        val item = items[position]

        holder.nickname?.text = item.nickname
        holder.title?.text = item.title
        holder.time?.text = formatRelativeTime(item.timestamp)
        holder.likeCount?.text = item.likes.toString()
        holder.commentCount?.text = item.comments.toString()

        if (!item.imageUrl.isNullOrBlank()) {
            holder.contentImage?.visibility = View.VISIBLE
            Glide.with(holder.itemView.context)
                .load(item.imageUrl)
                .centerCrop()
                .into(holder.contentImage!!)

        } else {
            holder.contentImage?.visibility = View.GONE
        }

    }

    override fun getItemCount(): Int {
        Log.d("MagazineAdapter", "getItemCount: ${items.size}")
        return items.size
    }

    private fun formatRelativeTime(timestamp: Long): String {
        return try {
            val now = System.currentTimeMillis()
            val diff = now - timestamp
            val minutes = diff / 60000
            val hours = minutes / 60
            val days = hours / 24

            when {
                minutes < 1 -> "방금 전"
                minutes < 60 -> "${minutes}분 전"
                hours < 24 -> "${hours}시간 전"
                else -> "${days}일 전"
            }
        } catch (e: Exception) {
            Log.e("MagazineAdapter", "Error formatting time", e)
            "시간 오류"
        }
    }
}