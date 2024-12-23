package com.example.pip_demo.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.VideoView
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.example.pip_demo.R


class MainActivity : AppCompatActivity() {

    private lateinit var videoView: VideoView
    private lateinit var videoUri: Uri
    private var isInCustomPiPMode = false
    private var windowManager: WindowManager? = null
    private var pipView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        videoView = findViewById(R.id.videoView)

        // Load video
        videoUri = Uri.parse("android.resource://" + packageName + "/" + R.raw.demo)
        videoView.setVideoURI(videoUri)
        videoView.start()

        findViewById<View>(R.id.pipButton).setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
                requestOverlayPermission()
            } else {
                enterCustomPiPMode()
            }
        }
    }

    private fun requestOverlayPermission() {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:$packageName")
        )
        startActivityForResult(intent, 101)
    }

    private fun enterCustomPiPMode() {
        if (isInCustomPiPMode) return

        isInCustomPiPMode = true
        videoView.pause()

        // Inflate custom PiP layout
        pipView = LayoutInflater.from(this).inflate(R.layout.custom_pip_window, null)
        val pipVideoView = pipView!!.findViewById<VideoView>(R.id.pipVideoView)
        val closeButton = pipView!!.findViewById<ImageButton>(R.id.closeButton)

        // Load video into PiP VideoView
        pipVideoView.setVideoURI(videoUri)
        pipVideoView.start()

        closeButton.setOnClickListener {
            exitCustomPiPMode()
        }

        // Set touch listener for drag
        pipView!!.setOnTouchListener(object : View.OnTouchListener {
            private var initialX = 0
            private var initialY = 0
            private var initialTouchX = 0f
            private var initialTouchY = 0f
            private lateinit var layoutParams: WindowManager.LayoutParams

            override fun onTouch(view: View, event: MotionEvent): Boolean {
                layoutParams = pipView!!.layoutParams as WindowManager.LayoutParams
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        initialX = layoutParams.x
                        initialY = layoutParams.y
                        initialTouchX = event.rawX
                        initialTouchY = event.rawY
                        return true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        layoutParams.x = initialX + (event.rawX - initialTouchX).toInt()
                        layoutParams.y = initialY + (event.rawY - initialTouchY).toInt()
                        windowManager!!.updateViewLayout(pipView, layoutParams)
                        return true
                    }
                }
                return false
            }
        })

        // Add PiP window to WindowManager
        val layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            else
                WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            android.graphics.PixelFormat.TRANSLUCENT
        )
        layoutParams.gravity = Gravity.TOP or Gravity.START
        layoutParams.x = 100
        layoutParams.y = 100

        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager!!.addView(pipView, layoutParams)
    }


    private fun exitCustomPiPMode() {
        if (!isInCustomPiPMode) return

        isInCustomPiPMode = false
        pipView?.let { windowManager?.removeView(it) }
        pipView = null

        videoView.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isInCustomPiPMode) {
            exitCustomPiPMode()
        }
    }
}
