package com.example.myapplication

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import android.os.Handler
import android.view.DisplayShape
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SplashFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class
SplashFragment : Fragment(R.layout.fragment_splash) {

    private val SPLASH_DURATION = 3000L

    private lateinit var imageRotate: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageRotate = view.findViewById(R.id.imageView2)

        val rotateAnimator = ObjectAnimator.ofFloat(imageRotate, "rotation", 0f, 360f)
        rotateAnimator.duration = SPLASH_DURATION
        rotateAnimator.repeatCount = ObjectAnimator.INFINITE
        rotateAnimator.repeatMode = ObjectAnimator.RESTART// Duration in milliseconds

        // Start the animation
        rotateAnimator.start()

         //Stop the animation after 3 seconds
        Handler(Looper.getMainLooper()).postDelayed({

            parentFragmentManager.beginTransaction().
                replace(R.id.fragmentContainerView, LoginFragment())
                .commit()
        }, 3000)

    }
}