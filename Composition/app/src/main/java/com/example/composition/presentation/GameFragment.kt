package com.example.composition.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.*
import com.example.composition.R
import com.example.composition.databinding.FragmentGameBinding
import com.example.composition.domain.entity.GameResult
import com.example.composition.domain.entity.Level
import com.example.composition.presentation.viewModels.GameViewModel

class GameFragment: Fragment() {

    private lateinit var level : Level

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            AndroidViewModelFactory.getInstance(requireActivity().application)
        )[GameViewModel::class.java]
    }

    private val options by lazy {
        mutableListOf<TextView>().apply {
            add(binding.tvOption1)
            add(binding.tvOption2)
            add(binding.tvOption3)
            add(binding.tvOption4)
            add(binding.tvOption5)
            add(binding.tvOption6)
        }
    }

    private var _binding:FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

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
        observeViewModel()
        viewModel.startGame(level)
        setOnClickOptionListeners()
    }

    private fun observeViewModel() {

        viewModel.question.observe(viewLifecycleOwner) {
            binding.tvSum.text = it.sum.toString()
            binding.tvLeftNumber.text = it.visibleNumber.toString()
            for (i in 0 until options.size ){
                options[i].text = it.operations[i].toString()
            }
        }


        viewModel.percentOfRightAnswers.observe(viewLifecycleOwner) {
            binding.progressBar.setProgress(it,true)
        }

        viewModel.enoughCountOfRightAnswers.observe(viewLifecycleOwner) {
            binding.tvAnswersProgress.setTextColor(getColorByState(it))

        }

        viewModel.enoughPercentOfRightAnswers.observe(viewLifecycleOwner){
            val color = getColorByState(it)
            binding.progressBar.progressTintList = ColorStateList.valueOf(color)
        }

        viewModel.formattedTime.observe(viewLifecycleOwner) {
            binding.tvTimer.text = it
        }

        viewModel.minPercent.observe(viewLifecycleOwner){
            binding.progressBar.secondaryProgress = it
        }

        viewModel.gameResult.observe(viewLifecycleOwner){
            launchGameFinishFragment(it)
        }

        viewModel.progressAnswer.observe(viewLifecycleOwner) {
            binding.tvAnswersProgress.text = it
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
            args.putParcelable(LEVEL_KEY,level)
            return GameFragment().apply {
                arguments = args
            }
        }
    }

    private fun setOnClickOptionListeners() {
        for (option in options) {
            option.setOnClickListener {
                val number = option.text.toString().toInt()
                viewModel.chooseAnswer(number)
            }
        }
    }

    private fun parseArgs(){
        requireArguments().getParcelable<Level>(LEVEL_KEY)?.let{
            level = it
        }
    }

    private fun launchGameFinishFragment(result: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(
                R.id.mainFragmentContainerView,
                GameFinishedFragment.newInstance(result)
            )
            .addToBackStack(null)
            .commit()
    }

    private fun getColorByState(goodState: Boolean): Int {
        val colorResId = if (goodState) {
            android.R.color.holo_green_light
        } else {
            android.R.color.holo_red_light
        }

        return ContextCompat.getColor(requireContext(), colorResId)
    }
}