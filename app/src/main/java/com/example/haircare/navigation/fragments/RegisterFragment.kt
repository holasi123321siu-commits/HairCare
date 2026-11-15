package com.example.haircare.navigation.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.haircare.R
import com.example.haircare.databinding.FragmentRegisterBinding
import com.example.haircare.navigation.viewmodels.RegisterUiEvent
import com.example.haircare.navigation.viewmodels.RegisterViewModel
import kotlinx.coroutines.launch

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentRegisterBinding.bind(view)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        val state = viewModel.uiState.value

        binding.etName.setText(state.name)
        binding.etEmail.setText(state.email)
        binding.etPassword.setText(state.password)
        binding.etConfirmPassword.setText(state.confirmPassword)

        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {

        binding.etName.doOnTextChanged { text, _, _, _ ->
            viewModel.onNameChanged(text.toString())
        }

        binding.etEmail.doOnTextChanged { text, _, _, _ ->
            viewModel.onEmailChanged(text.toString())
        }

        binding.etPassword.doOnTextChanged { text, _, _, _ ->
            viewModel.onPasswordChanged(text.toString())
        }

        binding.etConfirmPassword.doOnTextChanged { text, _, _, _ ->
            viewModel.onConfirmPasswordChanged(text.toString())
        }

        binding.btnRegister.setOnClickListener {
            viewModel.onRegisterClicked()
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeViewModel() {

        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.uiState.collect { state ->

                        binding.tilName.error =
                            state.nameError?.let { getString(it) }

                        binding.tilEmail.error =
                            state.emailError?.let { getString(it) }

                        binding.tilPassword.error =
                            state.passwordError?.let { getString(it) }

                        binding.tilConfirmPassword.error =
                            state.confirmPasswordError?.let { getString(it) }

                        binding.btnRegister.isEnabled =
                            state.isRegisterEnabled && !state.isLoading
                    }
                }

                launch {
                    viewModel.uiEvent.collect { event ->

                        when (event) {

                            RegisterUiEvent.RegisterSuccess -> {
                                Toast.makeText(
                                    requireContext(),
                                    R.string.register_success,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            is RegisterUiEvent.ShowError -> {
                                Toast.makeText(
                                    requireContext(),
                                    event.messageResId,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}