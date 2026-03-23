package com.example.compositionpractice.presentation


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.R
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.compositionpractice.databinding.FragmentGameBinding
import com.example.compositionpractice.domain.entity.Level
import com.example.compositionpractice.presentation.viewmodel.GameViewModel
import com.example.compositionpractice.presentation.viewmodel.GameViewModelFactory

class GameFragment : Fragment {
    private constructor()

    private lateinit var level: Level

    private val gameViewModelFactory by lazy {
        GameViewModelFactory(requireActivity().application,level)
    }

    private val options by lazy {
        mutableListOf<TextView>().apply{
            add(binding.tvOption1)
            add(binding.tvOption2)
            add(binding.tvOption3)
            add(binding.tvOption4)
            add(binding.tvOption5)
            add(binding.tvOption6)
        }
    }

    private val viewModel by lazy {
        ViewModelProvider(this,gameViewModelFactory)[GameViewModel::class.java]
    }

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding = null")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
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

        viewModelObservers()

        setOnOptionClickListener()
    }

    private fun viewModelObservers() {

        viewModel.timerLD.observe(viewLifecycleOwner) {
            binding.tvTimer.text = it
        }

        viewModel.questionLD.observe(viewLifecycleOwner) {
            with(binding) {
                tvSum.text = it.sum.toString()
                tvLeftNumber.text = it.visibleNumber.toString()
                for (i in 0 until it.options.size) {
                    options[i].text = it.options[i].toString()
                }
            }
        }

        viewModel.rightAnswersLD.observe(viewLifecycleOwner){
            binding.tvAnswersProgress.text = it
        }

        viewModel.precentRightAnswers.observe(viewLifecycleOwner){
            binding.progressBar.setProgress(it,true)
        }

        viewModel.minPercent.observe(viewLifecycleOwner){
            binding.progressBar.secondaryProgress = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private const val GAME_LEVEL = "GameLevel"


        fun newInstance(level: Level): GameFragment {
            val fragment = GameFragment()
            val args = Bundle()
            args.putParcelable(GAME_LEVEL, level)
            fragment.arguments = args
            return fragment
        }
    }


    private fun setOnOptionClickListener(){
        for (option in options){
            option.setOnClickListener {
                viewModel.answerQuestion(option.text.toString().toInt())
            }
        }
    }
    private fun parseArgs() {
        requireArguments().getParcelable<Level>(GAME_LEVEL)?.let {
            level = it
        }
    }
}