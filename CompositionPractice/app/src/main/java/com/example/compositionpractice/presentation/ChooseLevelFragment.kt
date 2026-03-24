package com.example.compositionpractice.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.compositionpractice.R
import com.example.compositionpractice.databinding.FragmentChooseLevelBinding
import com.example.compositionpractice.domain.entity.Level

class ChooseLevelFragment: Fragment {

    private constructor()
    private var _binding: FragmentChooseLevelBinding? = null
    private val binding: FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentChooseLevelBinding = null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseLevelBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLevelEasy.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(
                    R.id.mainFragmentContainer,
                    GameFragment.newInstance(Level.EASY)
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

        fun newInstance(): ChooseLevelFragment{
            val fragment =ChooseLevelFragment()
            return fragment
        }

    }
}