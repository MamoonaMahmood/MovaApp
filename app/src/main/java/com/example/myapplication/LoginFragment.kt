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

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signInBtn: Button = view.findViewById(R.id.button)
        val emailEt: EditText = view.findViewById(R.id.editTextEmail)
        val passwordEt : EditText = view.findViewById(R.id. editTextPassword)

        val emailStr : String = emailEt.text.trim().toString()
        val passwordStr : String = passwordEt.text.trim().toString()

        val cmpEmailStr: String = "abc@gmail.com"
        val cmpPasswordStr: String = "123456"


        signInBtn.setOnClickListener{

//            if(emailStr.equals(cmpEmailStr))
//                Toast.makeText(context, "Email Authenticated", Toast.LENGTH_SHORT).show()
//
//            if(passwordStr.equals(cmpPasswordStr))
//                Toast.makeText(context, "Password Authenticated", Toast.LENGTH_SHORT).show()
            val fragmentHome = HomeFragment()
            val nextTransaction = requireActivity().supportFragmentManager.beginTransaction()
                nextTransaction.replace(R.id.fragmentContainerView, fragmentHome)
                .addToBackStack(null)
                .commit()

        }





    }
}