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
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signInBtn.setOnClickListener{
            val nextTransaction = requireActivity().supportFragmentManager.beginTransaction()
                nextTransaction.replace(R.id.fragmentContainerView, FragmentAfterLogin())
                .addToBackStack(null)
                .commit()
        }
    }
}