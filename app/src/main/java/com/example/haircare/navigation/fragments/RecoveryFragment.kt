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
import com.example.haircare.databinding.FragmentRecoveryBinding
import com.example.haircare.navigation.viewmodels.RecoveryUiEvent
import com.example.haircare.navigation.viewmodels.RecoveryViewModel
import kotlinx.coroutines.launch

class RecoveryFragment : Fragment(R.layout.fragment_recovery) {

    private var _binding: FragmentRecoveryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecoveryViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentRecoveryBinding.bind(view)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        binding.etEmailRecovery.setText(viewModel.uiState.value.email)

        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {

        binding.etEmailRecovery.doOnTextChanged { text, _, _, _ ->
            viewModel.onEmailChanged(text.toString())
        }

        binding.btnSendRecovery.setOnClickListener {
            viewModel.onRecoveryClicked()
        }

        binding.btnBackLogin.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeViewModel() {

        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.uiState.collect { state ->

                        binding.tilEmailRecovery.error =
                            state.emailError?.let { getString(it) }

                        binding.btnSendRecovery.isEnabled =
                            state.isRecoveryEnabled && !state.isLoading
                    }
                }

                launch {
                    viewModel.uiEvent.collect { event ->

                        when (event) {

                            RecoveryUiEvent.RecoverySuccess -> {
                                Toast.makeText(
                                    requireContext(),
                                    R.string.recovery_success,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            is RecoveryUiEvent.ShowError -> {
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