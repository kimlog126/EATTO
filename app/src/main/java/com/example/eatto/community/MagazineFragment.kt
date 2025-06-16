package com.example.eatto.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.eatto.databinding.FragmentMagazineBinding

class MagazineFragment : Fragment() {

    private var _binding: FragmentMagazineBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMagazineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // 여기서 필요한 작업 예: 텍스트 설정 등
        binding.magazineText.text = "준비 중인 매거진 콘텐츠입니다."
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
