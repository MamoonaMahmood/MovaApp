package com.example.myapplication

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentAfterLogin.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentAfterLogin : NavHostFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var fragmentContainerView: FragmentContainerView


//   public val navController by lazy {
//      (parentFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment).navController
//   }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_after_login, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = childFragmentManager.findFragmentById(R.id.fragmentContainerView2)

        fragmentContainerView = view.findViewById<FragmentContainerView>(R.id.fragmentContainerView2)
        val navController = fragmentContainerView.findNavController()



        bottomNavigationView = view.findViewById(R.id.bottomNavView)
        bottomNavigationView.setupWithNavController(navController)

//        bottomNavigationView.setOnItemSelectedListener {  item ->
//            // Change color to red on selection
//            setBottomNavViewColor(ContextCompat.getColor(requireContext(), R.color.selected_bottom_nav))
//            true
//        }
//
//
//        setBottomNavViewColor(ContextCompat.getColor(requireContext(), R.color.default_bottom_nav))

    }

    private fun setBottomNavViewColor(color: Int) {
        bottomNavigationView.setBackgroundColor(color)
        bottomNavigationView.itemIconTintList = createColorStateList(color)
        bottomNavigationView.itemTextColor = createColorStateList(color)
    }

    private fun createColorStateList(color: Int): ColorStateList {
        return ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked),
                intArrayOf()
            ),
            intArrayOf(color, color) // selected color, default color
        )
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentAfterLogin.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentAfterLogin().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}