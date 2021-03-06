package com.lucascabral.drawapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

class DrawPath @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var paint: Paint = Paint()
    private var path: Path = Path()
    private var pathList = ArrayList<PaintPath>()
    private var undonePathList = ArrayList<PaintPath>()
    private var mX: Float = 0.0f
    private var mY: Float = 0.0f
    private var touchTolerance: Float = 4f

    init {
        paint.color = Color.GREEN
        paint.strokeWidth = 10f
        paint.style = Paint.Style.STROKE
        paint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas?) {
        if (pathList.size > 0) {
            for (paintPath in pathList) {
                canvas?.drawPath(paintPath.path, paint)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val xPos: Float = event.x
        val yPos: Float = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchStart(xPos, yPos)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                touchMove(xPos, yPos)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                touchUp()
                invalidate()
            }
        }
        invalidate()
        return true
    }

    private fun touchStart(xPos: Float, yPos: Float) {
        path = Path()
        val paintPath = PaintPath(path)
        pathList.add(paintPath)
        path.reset()
        path.moveTo(xPos, yPos)
        mX = xPos
        mY = yPos
    }

    private fun touchMove(xPos: Float, yPos: Float) {
        val dX: Float = abs(xPos - mX)
        val dY: Float = abs(yPos - mY)
        if (dX >= touchTolerance || dY >= touchTolerance) {
            path.quadTo(mX, mY, (xPos + mX) / 2, (yPos + mY) / 2)
            mX = xPos
            mY = yPos
        }
    }

    private fun touchUp() {
        path.lineTo(mX, mY)
    }

    fun undoPath() {
        val size = pathList.size
        if (size > 0) {
            undonePathList.add(pathList[size - 1])
            pathList.removeAt(size - 1)
            invalidate()
        }
    }

    fun redoPath() {
        val size = undonePathList.size
        if (size > 0) {
            pathList.add(undonePathList[size - 1])
            undonePathList.removeAt(size - 1)
            invalidate()
        }
    }

    fun deleteDrawing() {
        if (pathList.size > 0) {
            pathList.clear()
            undonePathList.clear()
            invalidate()
        }
    }
}