package com.example.haircare.navigation.fragments

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.haircare.R
import com.example.haircare.databinding.FragmentRecoveryBinding

class RecoveryFragment : Fragment(R.layout.fragment_recovery) {

    private lateinit var binding: FragmentRecoveryBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRecoveryBinding.bind(view)

        binding.btnSendRecovery.setOnClickListener {

            val email = binding.etEmailRecovery.text.toString().trim()

            if (email.isEmpty()) {
                binding.etEmailRecovery.error =
                    "Introduce tu correo electrónico"

            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.etEmailRecovery.error =
                    "El correo electrónico no es válido"

            } else {
                Toast.makeText(
                    requireContext(),
                    "Solicitud simulada correctamente",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.btnBackLogin.setOnClickListener {
            view.findNavController().navigateUp()
        }
    }
}
