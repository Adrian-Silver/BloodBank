package com.example.bloodbank2_0_main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import com.example.bloodbank2_0_main.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    internal val TIME_OUT = 3000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val topBottomAnimation = AnimationUtils.loadAnimation(this,R.anim.top_bottom_animation)
        val bottomTopAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_top_animation)
        val bounceAnimation = AnimationUtils.loadAnimation(this,R.anim.bounce_animation)

//        Grab id from XMl
        binding.ivDropImage.startAnimation(bounceAnimation)
//            tv_bounce.startAnimation(bounceAnimation)
        binding.tvDropText.startAnimation(bottomTopAnimation)



        Handler().postDelayed(
            {
                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            },TIME_OUT.toLong())



    }
}