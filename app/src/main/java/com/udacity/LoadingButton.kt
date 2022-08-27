package com.udacity

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.provider.Settings.Global.getString
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0
    private var buttonWidth = 0.0f
    var text = "Download"

    private var valueAnimator = ValueAnimator()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        typeface = Typeface.create( "", Typeface.BOLD)
        color = Color.GREEN
    }
    var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->
        if(new == ButtonState.Completed){
         // buttonWidth = width.toFloat()
        }
        else if (new == ButtonState.Loading){

        }
    }

fun x (){
    Log.i("button", "Loading")
    text = "We are Loading"
    valueAnimator = ValueAnimator.ofFloat(buttonWidth, widthSize.toFloat())
    valueAnimator.duration = 3000
    valueAnimator.interpolator = LinearInterpolator()
    Log.i("button", width.toString())

    valueAnimator.start()
    valueAnimator.addUpdateListener {
        //Log.i("button", it.animatedValue.toString())
        buttonWidth = it.animatedValue as Float
        text = "We are Loading"
        invalidate()
    }
    invalidate()
}
    init {
        isClickable = true

    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.color = Color.RED
        Log.i("button", width.toString())
        canvas?.drawRect(buttonWidth, 0.0f, width.toFloat(), height.toFloat(), paint)
        paint.color = Color.WHITE
        canvas?.drawText(text, (width/2.0).toFloat(), (height/1.5).toFloat(), paint )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }

}