package com.qz.rotateicons.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import com.qz.rotateicons.utils.dpToPx
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin
/*
*   Custom ViewGroup for rotate icon. Support both xml and code. we can customize background circle style, avatar
*   numbers and style, animation style.
*/
class RotateIconViewGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(
    context, attrs,
    defStyle, defStyleRes
) {
    private var avatarSize: Float? = null
    private val paint = Paint()
    private var circleColor: Int? = null
    private var circleCenterX: Float = 0f
    private var circleCenterY: Float = 0f
    private var radius = 0f
    private var currentAngle: Float = 0f

    private val defaultSize = 300f

    //Animation default configuration
    private var valueAnimator:ValueAnimator?=null
    private val defaultCycleTime = 15000L
    private val defaultAnimateDelay = 0L
    private val animateClockwise = true

    init {
        setWillNotDraw(false)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //support wrap content
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSpecSize = MeasureSpec.getSize(heightMeasureSpec)
        val minSize = defaultSize.dpToPx(context).toInt()
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(minSize, minSize)
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(minSize, heightSpecSize)
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, minSize)
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        circleCenterX = measuredWidth / 2f
        circleCenterY = measuredHeight / 2f
        val angleInterval: Float = calculateEvenlyAngle(childCount)
        radius = calculateBestRadius()
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val avatarRect = calculateAvatarRect(
                currentAngle,
                radius,
                avatarSize?.toInt() ?: child.measuredWidth,
                avatarSize?.toInt() ?: child.measuredHeight
            )
            child.layout(avatarRect.left, avatarRect.top, avatarRect.right, avatarRect.bottom)
            currentAngle = (currentAngle + angleInterval) % 360
        }
    }

    override fun onDraw(canvas: Canvas?) {
        drawBackgroundCircle(canvas)
        super.onDraw(canvas)
    }

    private fun calculateBestRadius(): Float {
        var maxChildSize = 0
        if (avatarSize != null) {
            maxChildSize = avatarSize!!.toInt()
        } else {
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                if (maxChildSize < max(child.measuredWidth, child.measuredHeight)) maxChildSize =
                    max(child.measuredWidth, child.measuredHeight)
            }
        }

        val maxRadius = (min(width, height) - maxChildSize) / 2
        return if (radius > maxRadius || radius == 0f) maxRadius.toFloat() else radius
    }


    private fun drawBackgroundCircle(canvas: Canvas?) {
        canvas?.run {
            paint.apply {
                reset()
                isAntiAlias = true
                style = Paint.Style.FILL
                color = resources.getColor(circleColor ?: android.R.color.holo_blue_light)
            }
            drawCircle(width / 2.toFloat(), height / 2.toFloat(), radius, paint)
        }
    }

    private fun calculateEvenlyAngle(totalAvatars: Int) =
        if (totalAvatars == 0) 0.toFloat() else (360.toFloat() / totalAvatars)

    //calculate coordinate base on rotate angle
    private fun calculateAvatarRect(
        currentAngle: Float,
        mRadius: Float,
        width: Int,
        height: Int
    ): Rect {
        val centX = sin(Math.PI / 180 * currentAngle) * mRadius + circleCenterX
        val centY = -cos(Math.PI / 180 * currentAngle) * mRadius + circleCenterY
        return Rect(
            (centX - width / 2).toInt(), (centY - height / 2).toInt(), (centX + width / 2).toInt(),
            (centY + height / 2).toInt()
        )
    }

    fun setFixedAvatarSizeInDP(size: Float) {
        avatarSize = size.dpToPx(context)
    }

    fun setCirCleRadiusInDP(radius: Float) {
        this.radius = radius.dpToPx(context)
    }

    fun setCirCleColor(color: Int) {
        circleColor = color
    }

    fun startAnimate(
        delay: Long = defaultAnimateDelay,
        cycleTime: Long = defaultCycleTime,
        clockWise: Boolean = animateClockwise
    ) {
        ValueAnimator.ofFloat(0f, 360f).apply {
            repeatMode = ValueAnimator.RESTART
            repeatCount = ValueAnimator.INFINITE
            startDelay = delay
            interpolator = LinearInterpolator()
            duration = cycleTime
            addUpdateListener {
                (it.animatedValue as? Float)?.let { value ->
                    currentAngle = value
                    requestLayout()
                }
            }
        }.let {
            valueAnimator=it
            if (clockWise) it.start() else it.reverse()
        }
    }

    fun stopAnimate(){
        valueAnimator?.cancel()
    }
}