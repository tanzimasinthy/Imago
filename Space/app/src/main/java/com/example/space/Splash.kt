package com.example.space

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_splash.*

class Splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)

        val random = (1..5).random()

        if (random == 1)
        {
            Glide.with(this).load(R.drawable.splash_1).centerCrop().into(splash)
        }
        else if (random ==2)
        {
            Glide.with(this).load(R.drawable.splash_2).centerCrop().into(splash)
        }
        else if (random ==3)
        {
            Glide.with(this).load(R.drawable.splash_3).centerCrop().into(splash)
        }
        else if (random ==4)
        {
            Glide.with(this).load(R.drawable.splash_4).centerCrop().into(splash)
        }
        else if (random ==5)
        {
            Glide.with(this).load(R.drawable.splash_5).centerCrop().into(splash)
        }

        //Glide.with(this).load(R.drawable.splash).centerCrop().into(splash)

        Handler().postDelayed(
            {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            },4036
        )
    }
}
