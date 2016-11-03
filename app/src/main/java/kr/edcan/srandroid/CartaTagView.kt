package kr.edcan.srandroid


import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.graphics.RectF
import android.util.AttributeSet
import android.view.Gravity
import android.widget.TextView


/**
 * Created by JunseokOh on 2016. 8. 6..
 */
class CartaTagView : TextView {
    internal var fullMode = false
    internal var textColorEnabled = false
    internal var color = Color.BLACK
    internal var textColor = Color.WHITE
    internal var height: Int = 0
    internal var width: Int = 0

    private var innerPaint: Paint? = null
    private var bgPaint: Paint? = null

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        getAttrs(attrs)
    }

    private fun getAttrs(attrs: AttributeSet) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.CartaTagView)
        setTypedArray(array)
    }

    private fun setTypedArray(array: TypedArray) {
        fullMode = array.getBoolean(R.styleable.CartaTagView_fullMode, false)
        color = array.getColor(R.styleable.CartaTagView_themeColor, Color.BLACK)
        textColor = array.getColor(R.styleable.CartaTagView_textThemeColor, Color.BLACK)
        textColorEnabled = array.getBoolean(R.styleable.CartaTagView_textThemeColorEnabled, false)
        array.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        height = measuredHeight
        width = measuredWidth
    }

    fun setView() {
        if (!textColorEnabled)
            setTextColor(if (fullMode) Color.WHITE else color)
        else
            setTextColor(textColor)
        gravity = Gravity.CENTER
    }

    override fun onDraw(canvas: Canvas) {
        setView()
        val center = Point(width / 2, height / 2)
        val strokeWidth = resources.getDimensionPixelSize(R.dimen.stroke_width)
        val innerH = height - strokeWidth
        val innerW = width - strokeWidth
        bgPaint = Paint()
        bgPaint!!.color = color
        bgPaint!!.style = Paint.Style.STROKE
        bgPaint!!.isAntiAlias = true
        bgPaint!!.strokeWidth = strokeWidth.toFloat()
        innerPaint = Paint()
        innerPaint!!.isAntiAlias = true
        innerPaint!!.color = color
        innerPaint!!.style = Paint.Style.FILL

        val left = center.x - innerW / 2
        val top = center.y - innerH / 2
        val right = center.x + innerW / 2
        val bottom = center.y + innerH / 2

        val bgRect = RectF(0.0f + strokeWidth, 0.0f + strokeWidth, (width - strokeWidth).toFloat(), (height - strokeWidth).toFloat())
        val innerRect = RectF(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())
        if (fullMode)
            canvas.drawRoundRect(innerRect, (innerH / 2).toFloat(), (innerH / 2).toFloat(), innerPaint!!)
        else
            canvas.drawRoundRect(bgRect, (height / 2).toFloat(), (height / 2).toFloat(), bgPaint!!)

        super.onDraw(canvas)
    }

    fun setShapeStyle(fullMode: Boolean, color: Int) {
        this.color = color
        this.fullMode = fullMode
        requestLayout()
    }


    fun setFullMode(fullMode: Boolean) {
        this.fullMode = fullMode
        requestLayout()
    }

    fun setShapeStyle(fullMode: Boolean, colorStr: String) {
        this.color = Color.parseColor(colorStr)
        requestLayout()
    }

    fun setTextColorForceFully(color: Int) {
        this.textColorEnabled = true
        this.textColor = color
        requestLayout()
    }

}
