package com.example.compositionpractice.presentation.viewmodel

import android.app.Application
import android.os.CountDownTimer
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.compositionpractice.R
import com.example.compositionpractice.data.GameRepositoryImpl
import com.example.compositionpractice.domain.entity.GameSettings
import com.example.compositionpractice.domain.entity.Level
import com.example.compositionpractice.domain.entity.Question
import com.example.compositionpractice.domain.usecases.GenerateQuestionUseCase
import com.example.compositionpractice.domain.usecases.GetGameSettingsUseCase

class GameViewModel(
    private val application: Application,
    private var level: Level
) : ViewModel(){


    private var  countOfRightAnswers = 0
    private var  countOfQuestions = 0
    private var gameSettings: GameSettings

    private val repository = GameRepositoryImpl

    private var timer: CountDownTimer? = null

    private val gameSettingsUseCase = GetGameSettingsUseCase(repository)
    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)

    private val _timer = MutableLiveData<String>()
    val timerLD: LiveData<String>
        get() = _timer

    private val _question = MutableLiveData<Question>()
    val questionLD: LiveData<Question>
        get() = _question

    private val _rightAnswers = MutableLiveData<String>()
    val rightAnswersLD: LiveData<String>
        get() = _rightAnswers

    private val _precentRightAnswers = MutableLiveData<Int>()
    val precentRightAnswers: LiveData<Int>
        get() = _precentRightAnswers

    private val _isRightAnswers = MutableLiveData<Boolean>()
    val isRightAnswers: LiveData<Boolean>
        get() = _isRightAnswers

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent


    init {
        gameSettings = gameSettingsUseCase(level)
        generateNewQuestion()
        startTimer()
        updateCountRightAnswers()
        _minPercent.value = gameSettings.minPercentOfRightAnswer
    }

    fun answerQuestion(answer:Int){
        checkAnswer(answer)
        generateNewQuestion()
        updateCountRightAnswers()
    }

    private fun checkAnswer(answer: Int) {
        if (answer == questionLD.value?.rightAnswer) {
            countOfRightAnswers++
        }
        countOfQuestions++
    }

    private fun updateCountRightAnswers() {
        _rightAnswers.value = String.format(
            application.resources.getString(R.string.progress_answers),
            countOfRightAnswers,
            gameSettings.minCountRightAnswers
        )

        _precentRightAnswers.value = ((countOfRightAnswers - countOfQuestions.toDouble())*100).toInt()
    }

    private fun generateNewQuestion() {
        _question.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }


    companion object{
        private const val SECOND_IN_MILLIS = 1000L
        private const val SECOND_IN_MINUTES = 60L

    }

    private fun startTimer(){
        val seconds = gameSettings.gameTimerInSeconds
        timer = object : CountDownTimer(seconds*SECOND_IN_MILLIS, SECOND_IN_MILLIS){
            override fun onFinish() {
                finish()
            }

            override fun onTick(millisUntilFinished: Long) {
                _timer.value = formatTimer(millisUntilFinished)
            }

        }
        timer?.start()
    }

    private fun formatTimer(millisUntilFinished: Long): String {
        val seconds = millisUntilFinished / SECOND_IN_MILLIS
        val minutes = seconds / SECOND_IN_MINUTES
        val leftSeconds = seconds - (minutes * SECOND_IN_MINUTES)
        return String.format("%02d:%02d", minutes, leftSeconds)
    }

    private fun finish(){

    }


    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

}