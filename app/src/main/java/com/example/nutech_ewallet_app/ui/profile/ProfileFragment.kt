package com.example.nutech_ewallet_app.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.nutech_ewallet_app.ViewModelFactory
import com.example.nutech_ewallet_app.databinding.FragmentProfileBinding
import com.example.nutech_ewallet_app.ui.home.HomeViewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        val factory = ViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(this, factory)[ProfileViewModel::class.java]

        val textView: TextView = binding.textNotifications
        viewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}