package com.example.nutech_ewallet_app.ui.transfer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.nutech_ewallet_app.ViewModelFactory
import com.example.nutech_ewallet_app.databinding.FragmentTransferBinding

class TransferFragment : Fragment() {

    private var _binding: FragmentTransferBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TransferViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val factory = ViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(this, factory)[TransferViewModel::class.java]

        _binding = FragmentTransferBinding.inflate(inflater, container, false)

        val textView: TextView = binding.textDashboard
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