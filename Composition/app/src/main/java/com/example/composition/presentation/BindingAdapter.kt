package com.example.composition.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.composition.R
import com.example.composition.domain.entity.GameResult
import com.example.composition.presentation.viewModels.GameViewModel


@BindingAdapter("requireResult")
fun bindRequiredArgs(textView: TextView,count:Int){
    textView.text = String.format(
        textView.context.getString(R.string.required_score),
        count
    )
}

@BindingAdapter("score")
fun bindingScore(textView: TextView,score:Int){
    textView.text = String.format(
        textView.context.getString(R.string.score_answers),
        score
    )
}


@BindingAdapter("requirePercent")
fun bindRequiredPercentArgs(textView: TextView,percent:Int){
    textView.text = String.format(
        textView.context.getString(R.string.required_percentage),
        percent
    )
}

@BindingAdapter("scorePercent")
fun bindPercent(textView: TextView,gameResult: GameResult){
    val precent =
        (gameResult.countOfRightAnswers / gameResult.countOfQuestions.toDouble() *100).toInt()

    textView.text = String.format(
        textView.context.getString(R.string.score_percentage),
        precent
    )
}


@BindingAdapter("imageSrc")
fun bindPercent(imageView: ImageView,winner: Boolean){
    val res = if (winner){
        R.drawable.ic_smile
    }else{
        R.drawable.ic_sad
    }
    imageView.setImageResource(res)
}

@BindingAdapter("enoughCount")
fun bindEnoughCount(textView: TextView,enough: Boolean) {
    textView.setTextColor(getColorByState(textView.context,enough))
}

@BindingAdapter("enoughPercent")
fun bindEnoughPercent(progressBar: ProgressBar,enough: Boolean) {
    progressBar.progressTintList = ColorStateList.valueOf(getColorByState(progressBar.context,enough))
}

@BindingAdapter("numberAsText")
fun bindNumberText(textView: TextView,number:Int) {
    textView.text = number.toString()
}



private fun getColorByState(context: Context, goodState: Boolean): Int {
    val colorResId = if (goodState) {
        android.R.color.holo_green_light
    } else {
        android.R.color.holo_red_light
    }

    return ContextCompat.getColor(context, colorResId)
}

@BindingAdapter("onOptionClickListener")
fun bindOptionClickListener(textView: TextView,clickListener:OnOptionClickListener){
    textView.setOnClickListener {
        clickListener.onOptionClick(textView.text.toString().toInt())
    }
}

interface OnOptionClickListener{
    fun onOptionClick(int: Int): Unit
}