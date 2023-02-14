package com.example.nutech_ewallet_app.ui.transaction

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nutech_ewallet_app.ViewModelFactory
import com.example.nutech_ewallet_app.data.entity.Transaction
import com.example.nutech_ewallet_app.databinding.FragmentTransactionBinding

class TransactionFragment : Fragment() {

    private var _binding: FragmentTransactionBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TransactionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val factory = ViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(this, factory)[TransactionViewModel::class.java]
        _binding = FragmentTransactionBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.transactions.observe(viewLifecycleOwner) {
            try {
                if (it?.data.isNullOrEmpty()) {
                    binding.emptyLayout.visibility = View.VISIBLE
                } else {
                    val layoutManager = LinearLayoutManager(requireContext())
                    binding.rvTransaction.layoutManager = layoutManager
                    val layoutAdapter = TransactionAdapter(it?.data!!)
                    binding.rvTransaction.adapter = layoutAdapter
                }
            } catch (e: Exception) {
                Log.e("Transaction Activity", e.message.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}