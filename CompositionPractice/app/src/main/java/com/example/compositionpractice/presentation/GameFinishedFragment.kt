package com.example.compositionpractice.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.compositionpractice.databinding.FragmentGameFinishedBinding
import com.example.compositionpractice.domain.entity.GameResult

class GameFinishedFragment: Fragment {

    private constructor()

    private var _binding: FragmentGameFinishedBinding? =null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding = null")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object{

        private const val GAME_RESULT = "GameResult"

        fun newInstance(gameResult: GameResult): GameFinishedFragment{
            return GameFinishedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(GAME_RESULT,gameResult)
                }
            }
        }

    }
}