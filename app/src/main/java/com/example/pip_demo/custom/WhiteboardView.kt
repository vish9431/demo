package com.example.pip_demo.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class WhiteboardView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val drawPaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 10f
        isAntiAlias = true
    }

    private val erasePaint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = 30f // Eraser width
        isAntiAlias = true
    }

    private val path = Path()
    private val paths = mutableListOf<Pair<Path, Paint>>()
    private var isEraserMode = false

    override fun onDraw(canvas: Canvas) {
        // Draw a white background to simulate a whiteboard
        canvas.drawColor(Color.WHITE)

        // Draw all paths with their corresponding paints
        for ((path, paint) in paths) {
            canvas.drawPath(path, paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(event.x, event.y)
                val currentPaint = if (isEraserMode) Paint(erasePaint) else Paint(drawPaint)
                paths.add(Pair(Path(path), currentPaint))
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(event.x, event.y)
                paths[paths.size - 1].first.lineTo(event.x, event.y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                path.reset()
            }
        }
        return true
    }

    // Enable drawing mode
    fun setDrawingMode() {
        isEraserMode = false
    }

    // Enable erasing mode
    fun setEraseMode() {
        isEraserMode = true
    }

    // Clear the whiteboard
    fun clearWhiteboard() {
        paths.clear()
        invalidate()
    }
}