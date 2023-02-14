package com.example.nutech_ewallet_app.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.nutech_ewallet_app.ViewModelFactory
import com.example.nutech_ewallet_app.databinding.FragmentHomeBinding
import com.example.nutech_ewallet_app.ui.topup.TopupActivity
import com.example.nutech_ewallet_app.ui.transfer.TransferActivity
import com.example.nutech_ewallet_app.utils.Utils

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val factory = ViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()

        binding.btnTopup.setOnClickListener {
            val intent = Intent(requireContext(), TopupActivity::class.java)
            startActivity(intent)
        }

        binding.btnTransfer.setOnClickListener {
            val intent = Intent(requireContext(), TransferActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("SetTextI18n")
    private fun setupData() {
        binding.saldo.setText("0")

        viewModel.userName.observe(viewLifecycleOwner) {
            try {
                if (it?.data != null) {
                    binding.appbarTitle.text = "Hello, ${it.data.firstName}!"
                }
            } catch (e: Exception) {
                Log.e("Home Activity", e.message.toString())
            }
        }

        viewModel.saldoBalance.observe(viewLifecycleOwner) {
            try {
                if (it?.data != null) {
                    val saldo = Utils.toIdrCurrency(it.data.balance!!).substringAfter("Rp")
                    binding.saldo.setText(saldo)
                } else {
                    binding.saldo.setText("0")
                }
            } catch (e: Exception) {
                Log.e("Home Activity", e.message.toString())
            }
        }
    }
}