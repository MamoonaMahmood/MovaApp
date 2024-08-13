package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import java.util.logging.Handler

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        if (savedInstanceState == null) {
            // Add SplashFragment to the container
            supportFragmentManager.beginTransaction()
                .add<SplashFragment>(R.id.fragmentContainerView)
                .commit()
        }

//        if (savedInstanceState == null) {
//            supportFragmentManager.commit {
//                setReorderingAllowed(true)
//                add<SplashFragment>(R.id.fragmentContainerView)
//            }
//        }
//
//        android.os.Handler().postDelayed({
//            replaceWithLoginFragment()
//        }, 6000)


    }

//    private fun replaceWithLoginFragment() {
//
//        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
//
//        supportFragmentManager.commit {
//            replace<LoginFragment>(R.id.fragmentContainerView)
//            setReorderingAllowed(true)
//        }
//
//    }

}






