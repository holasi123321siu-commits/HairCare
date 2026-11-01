package com.example.haircare.navigation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.haircare.R
import com.example.haircare.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment(R.layout.fragment_welcome) {

    private lateinit var binding: FragmentWelcomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentWelcomeBinding.bind(view)

        binding.btnLogin.setOnClickListener {
            view.findNavController().navigate(
                R.id.action_welcome_to_login
            )
        }

        binding.btnRegister.setOnClickListener {
            view.findNavController().navigate(
                R.id.action_welcome_to_register
            )
        }
    }
}
