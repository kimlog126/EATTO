package com.example.eatto.community

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eatto.R

class MagazineFragment : Fragment() {

    private lateinit var magazineAdapter: MagazineAdapter

    private val magazineList = listOf(
        MagazineItem("닥터브레드", "혈당을 지키는 한 끼, 오늘은 뭐 먹지?", "https://i.pinimg.com/736x/02/b6/09/02b60995f38bdd1ffc1ffc834d8bb5a7.jpg", System.currentTimeMillis() - 30 * 60 * 1000, 20, 3),
        MagazineItem("헬시한끼", "아침식사로 빵 아니면 밥, 그것이 문제로다", "https://i.pinimg.com/736x/ee/01/cb/ee01cbdf8567b14f99098aae3f399e3d.jpg", System.currentTimeMillis() - 2 * 60 * 60 * 1000, 14, 1)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        try {
            val view = inflater.inflate(R.layout.fragment_magazine, container, false)

            return view
        } catch (e: Exception) {

            return null
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("MagazineFragment", "onViewCreated called")

        try {
            val recyclerView = view.findViewById<RecyclerView>(R.id.magazineRecycler)
            if (recyclerView != null) {
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                magazineAdapter = MagazineAdapter(magazineList)
                recyclerView.adapter = magazineAdapter

            } else {

            }
        } catch (e: Exception) {

        }
    }
}