package com.example.nutech_ewallet_app.ui.registration

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.nutech_ewallet_app.ViewModelFactory
import com.example.nutech_ewallet_app.databinding.ActivityRegistrationBinding
import com.example.nutech_ewallet_app.ui.login.LoginActivity
import com.example.nutech_ewallet_app.utils.observeOnce

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var viewModel: RegistrationViewModel
    private var isFirstNameValid = true
    private var isLastNameValid = true
    private var isEmailValid = true
    private var isPasswordValid = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[RegistrationViewModel::class.java]

        binding.btnRegister.setOnClickListener {
            binding.apply {
                loadingLayout.loading.visibility = View.VISIBLE

                val firstName = edtFirstName.text.toString()
                val lastName = edtLastName.text.toString()
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()

                viewModel.registration(firstName, lastName, email, password)
                    .observe(this@RegistrationActivity) {

                    }
            }
        }

        viewAction()
    }

    private fun viewAction() {
        binding.apply {
            edtFirstName.doOnTextChanged { text, _, _, _ ->
                if (text!!.isEmpty()) {
                    edtlFirstName.error = "First Name is required"
                    isFirstNameValid = false
                } else {
                    edtlFirstName.isErrorEnabled = false
                    isFirstNameValid = true
                }
            }

            edtLastName.doOnTextChanged { text, _, _, _ ->
                if (text!!.isEmpty()) {
                    edtlLastName.error = "Last Name is required"
                    isLastNameValid = false
                } else {
                    edtlLastName.isErrorEnabled = false
                    isLastNameValid = true
                }
            }

            edtEmail.doOnTextChanged { text, _, _, _ ->
                when {
                    text!!.isEmpty() -> {
                        edtlEmail.error = "Email is required"
                        isEmailValid = false
                    }
                    else -> {
                        edtlEmail.isErrorEnabled = false
                        isEmailValid = true
                    }
                }
            }

            edtPassword.doOnTextChanged { text, _, _, _ ->
                when {
                    text!!.length in 1..5 -> {
                        edtlPassword.helperText = "*Minimum 6 characters required"
                        isPasswordValid = false
                    }
                    text.length in 6..10 -> {
                        edtlPassword.helperText = "Good Password"
                        isPasswordValid = true
                    }
                    text.length >= 11 -> {
                        edtlPassword.helperText = "Strong Password"
                        isPasswordValid = true
                    }
                    else -> {
                        edtlPassword.helperText = "*Minimum 6 characters required"
                        isPasswordValid = false
                    }
                }
            }

            btnRegister.setOnClickListener {
                register()
            }
        }
    }

    private fun register() {
        binding.apply {
            val firstName = edtFirstName.text.toString()
            val lastName = edtLastName.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            if (email.length < 3 || !email.contains("@")) {
                isEmailValid = false
                edtlEmail.error = "Email is not valid"
            }

            if (firstName.isEmpty()) {
                isFirstNameValid = false
                edtFirstName.error = "First name is required"
            }

            if (lastName.isEmpty()) {
                isFirstNameValid = false
                edtLastName.error = "Last name is required"
            }

            if (email.isEmpty()) {
                isEmailValid = false
                edtlEmail.error = "Email is required"
            }

            if (password.isEmpty()) {
                isPasswordValid = false
                edtlPassword.error = "Password Required"
            }

            if (password.length <= 5) {
                isPasswordValid = false
                edtlPassword.error = "*Minimum 6 characters required"
            }

            if (isFirstNameValid && isLastNameValid && isEmailValid && isPasswordValid) {
                loadingLayout.loading.visibility = View.VISIBLE
                viewModel.registration(firstName, lastName, email, password)
                    .observeOnce(this@RegistrationActivity) {
                        loadingLayout.loading.visibility = View.INVISIBLE
                        if (it?.status == 0) {
                            val showDialog = AlertDialog
                                .Builder(this@RegistrationActivity)
                                .setTitle("Success, ${it.message}")
                            showDialog.setPositiveButton("Login") {
                                    dialog, _ ->
                                val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
                                startActivity(intent)
                                dialog.dismiss()
                                finish()
                            }
                            showDialog.setNegativeButton("Back") {
                                    dialog, _ -> dialog.cancel()
                            }
                            showDialog.show()
                            clearData()
                        } else {
                            val showDialog = AlertDialog
                                .Builder(this@RegistrationActivity)
                                .setTitle(it?.message)
                            showDialog.setPositiveButton("Back") { dialog, _ ->
                                dialog.cancel()
                            }
                            showDialog.show()
                        }
                    }
            }
        }
    }

    private fun clearData() {
        binding.apply {
            edtFirstName.setText("")
            edtLastName.setText("")
            edtEmail.setText("")
            edtPassword.setText("")
            edtlFirstName.isErrorEnabled = false
            edtlLastName.isErrorEnabled = false
            edtlEmail.isErrorEnabled = false
            edtlPassword.isErrorEnabled = false
            edtFirstName.clearFocus()
            edtLastName.clearFocus()
            edtEmail.clearFocus()
            edtPassword.clearFocus()
        }
    }
}