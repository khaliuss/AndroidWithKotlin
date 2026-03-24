package com.example.compositionpractice.data

import com.example.compositionpractice.domain.entity.GameSettings
import com.example.compositionpractice.domain.entity.Level
import com.example.compositionpractice.domain.entity.Question
import com.example.compositionpractice.domain.repository.GameRepository
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object GameRepositoryImpl : GameRepository  {

    private const val MIN_SUM_VALUE = 2
    private const val MIN_ANSWER_VALUE = 1

    override fun getGameSettings(level: Level): GameSettings {
        return when(level){
            Level.TEST -> createGameSettings(Level.TEST)
            Level.EASY -> createGameSettings(Level.EASY)
            Level.NORMAL -> createGameSettings(Level.NORMAL)
            Level.HARD -> createGameSettings(Level.HARD)
        }
    }


    override fun generateQuestion(
        maxSumValue: Int,
        countOfOptions: Int
    ): Question {
        val sum  = Random.nextInt(MIN_SUM_VALUE,maxSumValue+1)
        val visibleNumber = Random.nextInt(MIN_ANSWER_VALUE,sum)
        val option = HashSet<Int>()
        val rightAnswer = sum - visibleNumber
        option.add(rightAnswer)
        val from = max(rightAnswer-countOfOptions,MIN_ANSWER_VALUE)
        val to = min(maxSumValue,rightAnswer+countOfOptions)
        while (option.size<countOfOptions){
            option.add(Random.nextInt(from,to))
        }

        return Question(sum,visibleNumber,option.toList())
    }



    private fun createGameSettings(level: Level): GameSettings{
        return when(level){
            Level.TEST -> GameSettings(
                10,
                3,
                50,
                8
            )

            Level.EASY -> GameSettings(
                10,
                10,
                70,
                60
            )

            Level.NORMAL -> GameSettings(
                20,
                20,
                80,
                40
            )

            Level.HARD -> GameSettings(
                30,
                30,
                90,
                30
            )
        }
    }

}