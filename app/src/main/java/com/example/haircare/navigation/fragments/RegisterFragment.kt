package com.example.haircare.navigation.fragments

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.haircare.R
import com.example.haircare.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterBinding.bind(view)

        binding.btnRegister.setOnClickListener {

            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()

            if (name.isEmpty()) {
                binding.etName.error = "Introduce tu nombre"

            } else if (email.isEmpty()) {
                binding.etEmail.error = "Introduce tu correo"

            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.etEmail.error = "Correo electrónico no válido"

            } else if (password.isEmpty()) {
                binding.etPassword.error = "Introduce una contraseña"

            } else if (password.length < 8) {
                binding.etPassword.error = "Mínimo 8 caracteres"

            } else if (confirmPassword.isEmpty()) {
                binding.etConfirmPassword.error = "Confirma tu contraseña"

            } else if (password != confirmPassword) {
                binding.etConfirmPassword.error = "Las contraseñas no coinciden"

            } else {
                Toast.makeText(
                    requireContext(),
                    "Formulario de registro válido",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
