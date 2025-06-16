package com.example.eatto.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eatto.R

class FeedAdapter(private val items: List<FeedItem>) :
    RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    inner class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nickname = itemView.findViewById<TextView>(R.id.nickname)
        val time = itemView.findViewById<TextView>(R.id.time)
        val content = itemView.findViewById<TextView>(R.id.content)
        val likeCount = itemView.findViewById<TextView>(R.id.likeCount)
        val commentCount = itemView.findViewById<TextView>(R.id.commentCount)
        val feedImage = itemView.findViewById<ImageView>(R.id.feedImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_feed, parent, false)
        return FeedViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val item = items[position]

        holder.nickname.text = item.nickname
        holder.time.text = item.timestamp?.let { formatRelativeTime(it) } ?: "알 수 없음"
        holder.content.text = item.content
        holder.likeCount.text = item.likes.toString()
        holder.commentCount.text = item.comments.toString()

        if (!item.imageUrl.isNullOrEmpty()) {
            holder.feedImage.visibility = View.VISIBLE
            Glide.with(holder.itemView.context)
                .load(item.imageUrl)
                .centerCrop()
                .into(holder.feedImage)
        } else {
            holder.feedImage.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = items.size

    private fun formatRelativeTime(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - timestamp

        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        return when {
            seconds < 60 -> "방금 전"
            minutes < 60 -> "${minutes}분 전"
            hours < 24 -> "${hours}시간 전"
            else -> "${days}일 전"
        }
    }
}
