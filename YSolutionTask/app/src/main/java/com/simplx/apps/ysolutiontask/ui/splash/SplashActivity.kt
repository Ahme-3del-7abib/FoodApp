package com.simplx.apps.ysolutiontask.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.animation.AlphaAnimation
import androidx.appcompat.app.AppCompatActivity
import com.simplx.apps.ysolutiontask.R
import com.simplx.apps.ysolutiontask.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity() {


    lateinit var animation: AlphaAnimation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        animation = AlphaAnimation(0.0f, 1.0f)
        animation.duration = 3000

        solutionsTv.startAnimation(animation)

        sleepDuration(3100)

    }

    private fun sleepDuration(time: Long) {
        val thread: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(time)
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        thread.start()
    }
}