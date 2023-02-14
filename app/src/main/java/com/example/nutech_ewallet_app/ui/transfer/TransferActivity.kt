package com.example.nutech_ewallet_app.ui.transfer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.nutech_ewallet_app.ViewModelFactory
import com.example.nutech_ewallet_app.databinding.ActivityTransferBinding
import java.text.NumberFormat

class TransferActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransferBinding
    private lateinit var viewModel: TransferViewModel
    private var userBalance = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[TransferViewModel::class.java]

        binding.edtAmount.addTextChangedListener(currencyWatcher)

        binding.btnTransfer.setOnClickListener {
            transfer()
        }
    }

    private fun transfer() {
        val amount = binding.edtAmount.text.toString().replace(",","").toInt()
        viewModel.userBalance.observe(this) {
            try {
                if (it?.data?.balance != null) {
                    userBalance = it.data.balance
                    if (userBalance < amount) {
                        AlertDialog
                            .Builder(this)
                            .setTitle("Your balance is not enough to make this transfer")
                            .setPositiveButton("Back") { listener, _ -> listener.dismiss() }
                            .show()
                    } else {
                        if (amount != null && amount >= 10_000) {
                            viewModel.transfer(amount).observe(this) {
                                if (it?.status == 0) {
                                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                            }
                        } else {
                            AlertDialog
                                .Builder(this)
                                .setTitle("Minimum transfer Rp 10,000")
                                .setPositiveButton("Back") { listener, _ -> listener.dismiss() }
                                .show()
                        }
                    }
                } else {
                    AlertDialog
                        .Builder(this)
                        .setTitle("Your balance is not enough to make this transfer")
                        .setPositiveButton("Back") { listener, _ -> listener.dismiss() }
                        .show()
                }
            } catch (e: Exception) {
                Log.e("Transfer Activity", e.message.toString())
            }

        }
    }

    private val currencyWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (!s.isNullOrEmpty()) {
                binding.edtAmount.removeTextChangedListener(this)

                val cleanStr = s.toString().replace(",", "").toDoubleOrNull()
                val formatted = NumberFormat.getNumberInstance().format(cleanStr)

                binding.edtAmount.setText(formatted)
                binding.edtAmount.setSelection(formatted.length)
                binding.edtAmount.addTextChangedListener(this)
            }
        }

        override fun afterTextChanged(s: Editable?) {}
    }
}