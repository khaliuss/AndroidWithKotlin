package com.example.compositionpractice.domain.repository

import com.example.compositionpractice.domain.entity.GameSettings
import com.example.compositionpractice.domain.entity.Level
import com.example.compositionpractice.domain.entity.Question

interface GameRepository {

    fun getGameSettings(level: Level): GameSettings

    fun generateQuestion(
        maxSumValue: Int,
        countOfOptions: Int
    ): Question


}