package com.example.haircare.navigation.fragments

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
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

            binding.tilName.error = null
            binding.tilEmail.error = null
            binding.tilPassword.error = null
            binding.tilConfirmPassword.error = null

            if (name.isEmpty()) {
                binding.tilName.error = getString(R.string.error_name)

            } else if (email.isEmpty()) {
                binding.tilEmail.error = getString(R.string.error_email)

            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.tilEmail.error = getString(R.string.error_email)

            } else if (password.length < 8) {
                binding.tilPassword.error = getString(R.string.error_password)

            } else if (confirmPassword.isEmpty() || password != confirmPassword) {
                binding.tilConfirmPassword.error =
                    getString(R.string.error_confirm_password)

            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.register_success),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.btnBack.setOnClickListener {
            view.findNavController().navigateUp()
        }
    }
}