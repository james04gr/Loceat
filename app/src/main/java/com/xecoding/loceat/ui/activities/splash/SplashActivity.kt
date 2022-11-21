package com.xecoding.loceat.ui.activities.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.xecoding.loceat.R
import com.xecoding.loceat.ui.activities.location.LocationActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Fake Splash screen with welcome animation
        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, LocationActivity::class.java))
            finish()
        }, 2000)
    }
}