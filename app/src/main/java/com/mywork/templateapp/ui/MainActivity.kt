package com.mywork.templateapp.ui

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mywork.templateapp.R
import com.mywork.templateapp.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private val openNextActivity = true

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        exitSplashScreen()
        observeData()
    }
    private fun observeData() {

    }

    private fun exitSplashScreen() {
        // Set up an OnPreDrawListener to the root view.
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    // Check if the initial data is ready.
                    return if (openNextActivity) {
                        // The content is ready; start drawing.
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        // The content is not ready; suspend.
                        false
                    }
                }
            }
        )

        //OR
/*
  // Add a callback that's called when the splash screen is animating to
          // the app content.
          splashScreen.setOnExitAnimationListener { splashScreenView ->
              // Create your custom animation.
              val slideUp = ObjectAnimator.ofFloat(
                  splashScreenView,
                  View.TRANSLATION_Y,
                  0f,
                  -splashScreenView.height.toFloat()
              )
              slideUp.interpolator = AnticipateInterpolator()
              slideUp.duration = 200L

              // Call SplashScreenView.remove at the end of your custom animation.
              slideUp.doOnEnd { splashScreenView.remove() }

              // Run your animation.
              slideUp.start()
          }
          */

    }

    override fun showProgressBar(show: Boolean) {
//        TODO("Not yet implemented")
    }

}