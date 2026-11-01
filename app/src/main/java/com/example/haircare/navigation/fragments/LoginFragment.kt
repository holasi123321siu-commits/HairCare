package com.example.haircare.navigation.fragments

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.haircare.R
import com.example.haircare.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginBinding.bind(view)

        binding.btnLogin.setOnClickListener {

            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString()

            if (email.isEmpty()) {
                binding.etEmail.error = "Introduce tu correo electrónico"
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.etEmail.error = "El correo electrónico no es válido"
            } else if (password.isEmpty()) {
                binding.etPassword.error = "Introduce tu contraseña"
            } else {
                Toast.makeText(
                    requireContext(),
                    "Formulario validado correctamente",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.btnRecovery.setOnClickListener {
            view.findNavController().navigate(
                R.id.action_login_to_recovery
            )
        }

        binding.btnBack.setOnClickListener {
            view.findNavController().navigateUp()
        }

    }
}
