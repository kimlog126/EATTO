package com.example.eatto.community

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eatto.databinding.FragmentFeedBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject

class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    private val feedList = mutableListOf<FeedItem>()
    private lateinit var feedAdapter: FeedAdapter

    private val db = FirebaseFirestore.getInstance()
    private var listenerRegistration: ListenerRegistration? = null

    private val handler = Handler()

    private val refreshRunnable = object : Runnable {
        override fun run() {
            feedAdapter.notifyDataSetChanged()
            handler.postDelayed(this, 60_000)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.feedRecycler.layoutManager = LinearLayoutManager(requireContext())
        feedAdapter = FeedAdapter(feedList)
        binding.feedRecycler.adapter = feedAdapter

        startListeningPosts()
        handler.post(refreshRunnable)
    }

    private fun startListeningPosts() {
        listenerRegistration?.remove()
        listenerRegistration = db.collection("feed_posts")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, e ->
                if (e != null || snapshot == null) return@addSnapshotListener

                feedList.clear()
                for (doc in snapshot.documents) {
                    val item = doc.toObject<FeedItem>()?.apply {
                        id = doc.id
                        if (timestamp == null) {
                            timestamp = System.currentTimeMillis()
                        }
                    }
                    if (item != null) feedList.add(item)
                }
                feedAdapter.notifyDataSetChanged()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        listenerRegistration?.remove()
        handler.removeCallbacks(refreshRunnable)
        _binding = null
    }
}
