package com.test.technokratos.loader

import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import com.test.technokratos.databinding.ActivityLoaderBinding
import com.test.technokratos.screen.MainActivity

class LoaderActivity : AppCompatActivity() {
    @Volatile
    private var transitionListener: MotionLayout.TransitionListener =
        object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {
                // In this place we can void GET request
            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
                // stub
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                runOnUiThread {
                    startActivity(Intent(this@LoaderActivity, MainActivity::class.java))
                }
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
            }

        }
    private lateinit var binding: ActivityLoaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoaderBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        binding.motionLayoutMain.setTransitionListener(transitionListener)
        binding.motionLayoutMain.startLayoutAnimation()
    }

    override fun onPause() {
        super.onPause()
        binding.motionLayoutMain.removeTransitionListener(transitionListener)
    }


}