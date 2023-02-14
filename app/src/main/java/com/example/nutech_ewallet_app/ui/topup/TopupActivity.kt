package com.example.nutech_ewallet_app.ui.topup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.nutech_ewallet_app.ViewModelFactory
import com.example.nutech_ewallet_app.databinding.ActivityTopupBinding
import java.text.NumberFormat

class TopupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTopupBinding
    private lateinit var viewModel: TopupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[TopupViewModel::class.java]
        binding.edtAmount.addTextChangedListener(currencyWatcher)

        binding.btnTopup.setOnClickListener {
            topup()
        }
    }

    private fun topup() {
        val amount = binding.edtAmount.text.toString().replace(",","").toInt()
        if (amount != null && amount >= 10_000) {
            viewModel.topup(amount).observe(this) {
                if (it?.status == 0) {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        } else {
            AlertDialog
                .Builder(this)
                .setTitle("Minimum top up Rp 10,000")
                .setPositiveButton("Back") { listener, _ -> listener.dismiss() }
                .show()
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