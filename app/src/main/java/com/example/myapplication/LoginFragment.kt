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

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var signInBtn: Button
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signInBtn = view.findViewById(R.id.button)
        emailEt = view.findViewById(R.id.editTextEmail)
        passwordEt = view.findViewById(R.id. editTextPassword)

        signInBtn.setOnClickListener{
                parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, FragmentAfterLogin())
                .commit()
        }
    }
}