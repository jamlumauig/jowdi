package jcb.bb.jowdi.Views.View

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import jcb.bb.jowdi.R

class SplashActvity : AppCompatActivity() {
    private lateinit var inlogo: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_layout)
        inlogo = findViewById(R.id.splashid)
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        startSplash()
    }

    private fun startSplash() {
        val handler = Handler()
        handler.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)

    }
}