package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.composition.R
import com.example.composition.databinding.FragmentGameBinding
import com.example.composition.domain.entity.GameResult
import com.example.composition.domain.entity.GameSettings
import com.example.composition.domain.entity.Level

class GameFragment: Fragment() {

    private lateinit var level : Level

    private var _binding:FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()

        when(level){
            Level.TEST -> {}
            Level.EASY -> {}
            Level.NORMAL -> {}
            Level.HARD -> {}
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvSum.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(
                    R.id.mainFragmentContainerView,
                    GameFinishedFragment.newInstance(GameResult(true,20,20, GameSettings(2,3,4,5)))
                )
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    companion object{

        const val LEVEL_KEY = "level"
        const val NAME = "GameFragment"

        fun newInstance(level: Level): GameFragment{
            val args = Bundle()
            args.putSerializable(LEVEL_KEY,level)
            return GameFragment().apply {
                arguments = args
            }
        }
    }

    private fun parseArgs(){
        level =  requireArguments().getSerializable(LEVEL_KEY) as  Level
    }
}