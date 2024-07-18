package com.example.myapplication

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginFragment : Fragment() {

    private lateinit var signInBtn: Button
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    companion object {
        fun newInstance() = LoginFragment()
    }

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_login, container, false)
        signInBtn = view.findViewById(R.id.button)
        emailEt = view.findViewById(R.id.editTextEmail)
        passwordEt = view.findViewById(R.id. editTextPassword)

//        val emailStr : String = emailEt.text.trim().toString()
//        val passwordStr : String = passwordEt.text.trim().toString()
//
//        val cmpEmailStr: String = "abc@gmail.com"
//        val cmpPasswordStr: String = "123456"
//
//
//        signInBtn.setOnClickListener{
//
//            if(!(emailStr.equals(cmpEmailStr)) || !(passwordStr.equals(cmpPasswordStr))) {
//                Toast.makeText(context, "Fill relevant fields accordingly", Toast.LENGTH_SHORT).show()
//            }
//
//            val fragmentHome = HomeFragment()
//            val nextTransaction = requireActivity().supportFragmentManager.beginTransaction()
//            nextTransaction.replace(R.id.fragmentContainerView, fragmentHome)
//                .addToBackStack(null)
//                .commit()
//
//        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emailStr : String = emailEt.text.trim().toString()
        val passwordStr : String = passwordEt.text.trim().toString()

        val cmpEmailStr: String = "abc@gmail.com"
        val cmpPasswordStr: String = "123456"


        signInBtn.setOnClickListener{

            val fragmentHome = HomeFragment()
            val nextTransaction = requireActivity().supportFragmentManager.beginTransaction()
                nextTransaction.replace(R.id.fragmentContainerView, fragmentHome)
                .addToBackStack(null)
                .commit()

        }
    }
}