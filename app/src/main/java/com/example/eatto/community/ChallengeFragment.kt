package com.example.eatto.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eatto.databinding.FragmentChallengeBinding

class ChallengeFragment : Fragment() {

    private var _binding: FragmentChallengeBinding? = null
    private val binding get() = _binding!!

    private lateinit var challengeAdapter: ChallengeAdapter

    private val challengeList = listOf(
        ChallengeItem("기본 습관 형성", "하루 한 끼 건강 밥상", "5.1(목) ~ 5.26(월)", "식사 인증\n221명 참여중", "3,000P"),
        ChallengeItem("깜짝 챌린지", "찾아라! 슈퍼 푸드", "4.15(화) ~ 4.21(월)", "식사 인증\n300명 참여 중", "3,000P")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChallengeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.challengeRecycler.layoutManager = LinearLayoutManager(requireContext())
        challengeAdapter = ChallengeAdapter(challengeList)
        binding.challengeRecycler.adapter = challengeAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
