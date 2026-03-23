package com.example.compositionpractice.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GameResult(
    val winner: Boolean,
    val countOfRightAnswers:Int,
    val percentOfRightAnswers:Int,
    val gameSettings: GameSettings
): Parcelable