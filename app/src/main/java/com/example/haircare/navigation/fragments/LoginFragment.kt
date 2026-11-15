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
import com.example.haircare.databinding.FragmentLoginBinding
import com.example.haircare.navigation.viewmodels.LoginUiEvent
import com.example.haircare.navigation.viewmodels.LoginViewModel
import kotlinx.coroutines.launch

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentLoginBinding.bind(view)

        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {

        binding.etEmail.doOnTextChanged { text, _, _, _ ->
            viewModel.onEmailChanged(text.toString())
        }

        binding.etPassword.doOnTextChanged { text, _, _, _ ->
            viewModel.onPasswordChanged(text.toString())
        }

        binding.btnLogin.setOnClickListener {
            viewModel.onLoginClicked()
        }

        binding.btnRecovery.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_recovery)
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

                        if (binding.etEmail.text.toString() != state.email) {
                            binding.etEmail.setText(state.email)
                        }

                        if (binding.etPassword.text.toString() != state.password) {
                            binding.etPassword.setText(state.password)
                        }

                        binding.tilEmail.error =
                            state.emailError?.let { getString(it) }

                        binding.tilPassword.error =
                            state.passwordError?.let { getString(it) }

                        binding.btnLogin.isEnabled =
                            state.isLoginEnabled && !state.isLoading
                    }
                }

                launch {
                    viewModel.uiEvent.collect { event ->

                        when (event) {

                            LoginUiEvent.NavigateToHome -> {
                                findNavController().navigate(
                                    R.id.action_login_to_home
                                )
                            }

                            is LoginUiEvent.ShowError -> {
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