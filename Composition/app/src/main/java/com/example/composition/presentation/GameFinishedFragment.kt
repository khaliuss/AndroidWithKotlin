package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.example.composition.R
import com.example.composition.databinding.FragmentGameFinishedBinding
import com.example.composition.domain.entity.GameResult

class GameFinishedFragment : Fragment() {
    private lateinit var gameResult: GameResult
    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonRetry.setOnClickListener {
            retryGame()
        }


        binding.tvRequiredAnswers.text = getString(
            R.string.required_score,
            gameResult.gameSettings.minCountOfRightAnswers.toString()
        )

        binding.tvScoreAnswers.text = getString(
            R.string.score_answers,
            gameResult.countOfRightAnswers.toString()
        )

        binding.tvRequiredPercentage.text = getString(
            R.string.required_percentage,
            gameResult.gameSettings.minPercentOfRightAnswers.toString()
        )
        val precent = (gameResult.countOfRightAnswers / gameResult.countOfQuestions.toDouble() *100).toInt()
        binding.tvScorePercentage.text = getString(
            R.string.score_percentage,
            precent.toString()

        )

        val resDrawable = if (gameResult.winner){
            R.drawable.ic_smile
        }else{
            R.drawable.ic_sad
        }

        binding.emojiResult.setImageDrawable(ContextCompat.getDrawable(view.context,resDrawable))

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        const val RESULT_KEY = "result"

        fun newInstance(gameResult: GameResult): GameFinishedFragment {
            val args = Bundle()
            args.putParcelable(RESULT_KEY, gameResult)
            return GameFinishedFragment().apply {
                arguments = args
            }
        }

    }

    fun retryGame() {
        findNavController().popBackStack()
    }

    private fun parseArgs() {
        requireArguments().getParcelable<GameResult>(RESULT_KEY)?.let {
            gameResult = it
        }
    }

}