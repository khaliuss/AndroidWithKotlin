package com.example.composition.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.composition.R
import com.example.composition.databinding.FragmentChooseLevelBinding
import com.example.composition.domain.entity.Level

class ChooseLevelFragment : Fragment() {

    private var _binding: FragmentChooseLevelBinding? = null
    private val binding: FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentChooseLevelBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonListeners()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        const val NAME = "ChooseLevelFragment"

        fun newInstance(): ChooseLevelFragment {
            return ChooseLevelFragment()
        }

    }

    private fun startFragment(level: Level) {
        val args = Bundle().apply {
            putParcelable(GameFragment.LEVEL_KEY, level)
        }

        findNavController().navigate(R.id.action_chooseLevelFragment_to_gameFragment, args)
    }

    private fun buttonListeners() {

        with(binding) {
            buttonLevelTest.setOnClickListener {
                startFragment(Level.TEST)
            }

            buttonLevelEasy.setOnClickListener {
                startFragment(Level.EASY)
            }

            buttonLevelNormal.setOnClickListener {
                startFragment(Level.NORMAL)
            }

            buttonLevelHard.setOnClickListener {
                startFragment(Level.HARD)
            }
        }
    }
}