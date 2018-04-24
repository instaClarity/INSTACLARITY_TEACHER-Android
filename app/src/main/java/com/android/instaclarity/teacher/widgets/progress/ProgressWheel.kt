package com.android.instaclarity.teacher.widgets.progress

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.Style
import android.graphics.RectF
import android.os.Parcel
import android.os.Parcelable
import android.os.SystemClock
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.android.instaclarity.teacher.R

/**
 * A Material style progress wheel, compatible up to 2.2.
 */
class ProgressWheel : View {
    //Paints
    private val barPaint = Paint()
    private val rimPaint = Paint()
    private var circleRadius = 28
    private var barWidth = 4
    private var rimWidth = 4
    private var fillRadius = false
    private var timeStartGrowing = 0.0
    private var barSpinCycleTime = 460.0
    private var barExtraLength = 0f
    private var barGrowingFromFront = true
    private var pausedTimeWithoutGrowing: Long = 0
    //Colors (with defaults)
    private var barColor = -0x56000000
    private var rimColor = 0x00FFFFFF
    //Rectangles
    private var circleBounds = RectF()

    //Animation
    //The amount of degrees per second
    private var spinSpeed = 230.0f

    // The last time the spinner was animated
    private var lastTimeAnimated: Long = 0
    private var linearProgress: Boolean = false
    private var progress = 0.0f
    private var targetProgress = 0.0f
    private var isSpinning = false

    /**
     * The constructor for the ProgressWheel
     *
     * @param context the context
     * @param attrs   the attrs
     */
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        parseAttributes(context.obtainStyledAttributes(attrs, R.styleable.ProgressWheel))
    }

    /**
     * The constructor for the ProgressWheel
     *
     * @param context the context
     */
    constructor(context: Context) : super(context) {}

    //----------------------------------
    //Setting up stuff
    //----------------------------------

    /**
     * On measure.
     *
     * @param widthMeasureSpec  the width measure spec
     * @param heightMeasureSpec the height measure spec
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val viewWidth = circleRadius + this.paddingLeft + this.paddingRight
        val viewHeight = circleRadius + this.paddingTop + this.paddingBottom

        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)

        val width: Int
        val height: Int

        //Measure Width
        if (widthMode == View.MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize
        } else if (widthMode == View.MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(viewWidth, widthSize)
        } else {
            //Be whatever you want
            width = viewWidth
        }

        //Measure Height
        if (heightMode == View.MeasureSpec.EXACTLY || widthMode == View.MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize
        } else if (heightMode == View.MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(viewHeight, heightSize)
        } else {
            //Be whatever you want
            height = viewHeight
        }

        setMeasuredDimension(width, height)
    }

    /**
     * Use onSizeChanged instead of onAttachedToWindow to get the dimensions of the view, because
     * This method is called after measuring the dimensions of MATCH_PARENT & WRAP_CONTENT. Use this
     * dimensions to setup the bounds and paints.
     *
     * @param width     the width
     * @param height    the height
     * @param oldWidth  the old width
     * @param oldHeight the old height
     */
    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)

        setupBounds(width, height)
        setupPaints()
        invalidate()
    }

    /**
     * Set the properties of the paints we're using to draw the progress wheel
     */
    private fun setupPaints() {
        barPaint.color = barColor
        barPaint.isAntiAlias = true
        barPaint.style = Style.STROKE
        barPaint.strokeWidth = barWidth.toFloat()

        rimPaint.color = rimColor
        rimPaint.isAntiAlias = true
        rimPaint.style = Style.STROKE
        rimPaint.strokeWidth = rimWidth.toFloat()
    }

    /**
     * Set the bounds of the component
     *
     * @param layoutWidth  the layout width
     * @param layoutHeight the layout height
     */
    private fun setupBounds(layoutWidth: Int, layoutHeight: Int) {
        val paddingTop = paddingTop
        val paddingBottom = paddingBottom
        val paddingLeft = paddingLeft
        val paddingRight = paddingRight

        if (!fillRadius) {
            // Width should equal to Height, find the min value to setup the circle
            val minValue = Math.min(layoutWidth - paddingLeft - paddingRight,
                    layoutHeight - paddingBottom - paddingTop)

            val circleDiameter = Math.min(minValue, circleRadius * 2 - barWidth * 2)

            // Calc the Offset if needed for centering the wheel in the available space
            val xOffset = (layoutWidth - paddingLeft - paddingRight - circleDiameter) / 2 + paddingLeft
            val yOffset = (layoutHeight - paddingTop - paddingBottom - circleDiameter) / 2 + paddingTop

            circleBounds = RectF((xOffset + barWidth).toFloat(),
                    (yOffset + barWidth).toFloat(),
                    (xOffset + circleDiameter - barWidth).toFloat(),
                    (yOffset + circleDiameter - barWidth).toFloat())
        } else {
            circleBounds = RectF((paddingLeft + barWidth).toFloat(),
                    (paddingTop + barWidth).toFloat(),
                    (layoutWidth - paddingRight - barWidth).toFloat(),
                    (layoutHeight - paddingBottom - barWidth).toFloat())
        }
    }

    /**
     * Parse the attributes passed to the view from the XML
     *
     * @param a the attributes to parse
     */
    private fun parseAttributes(a: TypedArray) {
        // We transform the default values from DIP to pixels
        val metrics = context.resources.displayMetrics
        barWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, barWidth.toFloat(), metrics).toInt()
        rimWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rimWidth.toFloat(), metrics).toInt()
        circleRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, circleRadius.toFloat(), metrics).toInt()

        circleRadius = a.getDimension(R.styleable.ProgressWheel_matProg_circleRadius, circleRadius.toFloat()).toInt()

        fillRadius = a.getBoolean(R.styleable.ProgressWheel_matProg_fillRadius, false)

        barWidth = a.getDimension(R.styleable.ProgressWheel_matProg_barWidth, barWidth.toFloat()).toInt()

        rimWidth = a.getDimension(R.styleable.ProgressWheel_matProg_rimWidth, rimWidth.toFloat()).toInt()

        val baseSpinSpeed = a.getFloat(R.styleable.ProgressWheel_matProg_spinSpeed, spinSpeed / 360.0f)
        spinSpeed = baseSpinSpeed * 360

        barSpinCycleTime = a.getInt(R.styleable.ProgressWheel_matProg_barSpinCycleTime, barSpinCycleTime.toInt()).toDouble()

        barColor = a.getColor(R.styleable.ProgressWheel_matProg_barColor, barColor)

        rimColor = a.getColor(R.styleable.ProgressWheel_matProg_rimColor, rimColor)

        linearProgress = a.getBoolean(R.styleable.ProgressWheel_matProg_linearProgress, false)

        if (a.getBoolean(R.styleable.ProgressWheel_matProg_progressIndeterminate, false)) {
            spin()
        }

        // Recycle
        a.recycle()
    }

    //----------------------------------
    //Animation stuff
    //----------------------------------

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawArc(circleBounds, 360f, 360f, false, rimPaint)

        var mustInvalidate = false

        if (isSpinning) {
            //Draw the spinning bar
            mustInvalidate = true

            val deltaTime = SystemClock.uptimeMillis() - lastTimeAnimated
            val deltaNormalized = deltaTime * spinSpeed / 1000.0f

            updateBarLength(deltaTime)

            progress += deltaNormalized
            if (progress > 360) {
                progress -= 360f

            }
            lastTimeAnimated = SystemClock.uptimeMillis()

            var from = progress - 90
            var length = BAR_LENGTH + barExtraLength

            if (isInEditMode) {
                from = 0f
                length = 135f
            }

            canvas.drawArc(circleBounds, from, length, false,
                    barPaint)
        } else {
            if (java.lang.Float.floatToIntBits(progress) != java.lang.Float.floatToIntBits(targetProgress)) {
                //We smoothly increase the progress bar
                mustInvalidate = true

                val deltaTime = (SystemClock.uptimeMillis() - lastTimeAnimated).toFloat() / 1000
                val deltaNormalized = deltaTime * spinSpeed

                progress = Math.min(progress + deltaNormalized, targetProgress)
                lastTimeAnimated = SystemClock.uptimeMillis()
            }

            var offset = 0.0f
            var tempProgress = this.progress
            if (!linearProgress) {
                val factor = 2.0f
                offset = (1.0f - Math.pow((1.0f - this.progress / 360.0f).toDouble(), (2.0f * factor).toDouble())).toFloat() * 360.0f
                tempProgress = (1.0f - Math.pow((1.0f - this.progress / 360.0f).toDouble(), factor.toDouble())).toFloat() * 360.0f
            }

            if (isInEditMode) {
                tempProgress = 360f
            }

            canvas.drawArc(circleBounds, offset - 90, tempProgress, false, barPaint)
        }

        if (mustInvalidate) {
            invalidate()
        }
    }

    /**
     * On visibility changed.
     *
     * @param changedView the changed view
     * @param visibility  the visibility
     */
    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)

        if (visibility == View.VISIBLE) {
            lastTimeAnimated = SystemClock.uptimeMillis()
        }
    }

    /**
     * Update bar length.
     *
     * @param deltaTimeInMilliSeconds the delta time in milli seconds
     */
    private fun updateBarLength(deltaTimeInMilliSeconds: Long) {
        if (pausedTimeWithoutGrowing >= PAUSE_GROWING_TIME) {
            timeStartGrowing += deltaTimeInMilliSeconds.toDouble()

            if (timeStartGrowing > barSpinCycleTime) {
                // We completed a size change cycle
                // (growing or shrinking)
                timeStartGrowing -= barSpinCycleTime
                pausedTimeWithoutGrowing = 0
                barGrowingFromFront = !barGrowingFromFront
            }

            val distance = Math.cos((timeStartGrowing / barSpinCycleTime + 1) * Math.PI).toFloat() / 2 + 0.5f
            val destLength = BAR_MAX_LENGTH.toFloat() - BAR_LENGTH

            if (barGrowingFromFront) {
                barExtraLength = distance * destLength
            } else {
                val newLength = destLength * (1 - distance)
                progress += barExtraLength - newLength
                barExtraLength = newLength
            }
        } else {
            pausedTimeWithoutGrowing += deltaTimeInMilliSeconds
        }
    }

    /**
     * Puts the view on spin mode
     */
    fun spin() {
        lastTimeAnimated = SystemClock.uptimeMillis()
        isSpinning = true
        invalidate()
    }

    /**
     * On save instance state parcelable.
     *
     * @return the parcelable
     */
    // Great way to save a view's state http://stackoverflow.com/a/7089687/1991053
    public override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()

        val ss = WheelSavedState(superState)

        // We save everything that can be changed at runtime
        ss.progress = this.progress
        ss.targetProgress = this.targetProgress
        ss.isSpinning = this.isSpinning
        ss.spinSpeed = this.spinSpeed
        ss.barWidth = this.barWidth
        ss.barColor = this.barColor
        ss.rimWidth = this.rimWidth
        ss.rimColor = this.rimColor
        ss.circleRadius = this.circleRadius
        ss.linearProgress = this.linearProgress
        ss.fillRadius = this.fillRadius

        return ss
    }

    /**
     * On restore instance state.
     *
     * @param state the state
     */
    public override fun onRestoreInstanceState(state: Parcelable) {
        if (state !is WheelSavedState) {
            super.onRestoreInstanceState(state)
            return
        }

        super.onRestoreInstanceState(state.superState)

        this.progress = state.progress
        this.targetProgress = state.targetProgress
        this.isSpinning = state.isSpinning
        this.spinSpeed = state.spinSpeed
        this.barWidth = state.barWidth
        this.barColor = state.barColor
        this.rimWidth = state.rimWidth
        this.rimColor = state.rimColor
        this.circleRadius = state.circleRadius
        this.linearProgress = state.linearProgress
        this.fillRadius = state.fillRadius

        this.lastTimeAnimated = SystemClock.uptimeMillis()
    }

    /**
     * The type Wheel saved state.
     */
    private class WheelSavedState : View.BaseSavedState {

        var progress: Float = 0.0f
         var targetProgress: Float = 0.0f
        var isSpinning: Boolean = false
        var spinSpeed: Float = 0.0f
        var barWidth: Int = 0
        var barColor: Int = 0
        var rimWidth: Int = 0
        var rimColor: Int = 0
        var circleRadius: Int = 0
        var linearProgress: Boolean = false
        var fillRadius: Boolean = false

        /**
         * Instantiates a new Wheel saved state.
         *
         * @param superState the super state
         */
        internal constructor(superState: Parcelable) : super(superState) {}

        private constructor(`in`: Parcel) : super(`in`) {
            this.progress = `in`.readFloat()
            this.targetProgress = `in`.readFloat()
            this.isSpinning = `in`.readByte().toInt() != 0
            this.spinSpeed = `in`.readFloat()
            this.barWidth = `in`.readInt()
            this.barColor = `in`.readInt()
            this.rimWidth = `in`.readInt()
            this.rimColor = `in`.readInt()
            this.circleRadius = `in`.readInt()
            this.linearProgress = `in`.readByte().toInt() != 0
            this.fillRadius = `in`.readByte().toInt() != 0
        }

        /**
         * Write to parcel.
         *
         * @param out   the out
         * @param flags the flags
         */
        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeFloat(this.progress)
            out.writeFloat(this.targetProgress)
            out.writeByte((if (isSpinning) 1 else 0).toByte())
            out.writeFloat(this.spinSpeed)
            out.writeInt(this.barWidth)
            out.writeInt(this.barColor)
            out.writeInt(this.rimWidth)
            out.writeInt(this.rimColor)
            out.writeInt(this.circleRadius)
            out.writeByte((if (linearProgress) 1 else 0).toByte())
            out.writeByte((if (fillRadius) 1 else 0).toByte())
        }

        companion object {
            /**
             * The constant CREATOR.
             */
            val CREATOR: Parcelable.Creator<WheelSavedState> = object : Parcelable.Creator<WheelSavedState> {
                override fun createFromParcel(`in`: Parcel): WheelSavedState {
                    return WheelSavedState(`in`)
                }

                override fun newArray(size: Int): Array<WheelSavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }

    companion object {
        private val BAR_MAX_LENGTH = 270
        private val PAUSE_GROWING_TIME: Long = 200
        private val BAR_LENGTH = 16
    }
}
