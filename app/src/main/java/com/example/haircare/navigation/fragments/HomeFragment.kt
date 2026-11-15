package com.example.haircare.navigation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.haircare.R
import com.example.haircare.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentHomeBinding.bind(view)

        binding.btnBackHome.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_welcome)
        }
    }
}