package com.example.compositionpractice.domain.usecases

import com.example.compositionpractice.domain.entity.GameSettings
import com.example.compositionpractice.domain.entity.Level
import com.example.compositionpractice.domain.repository.GameRepository

class GetGameSettingsUseCase(private val repository: GameRepository) {

    operator fun invoke(level: Level): GameSettings{
        return repository.getGameSettings(level)
    }

}