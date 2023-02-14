package com.example.nutech_ewallet_app.ui.login

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.nutech_ewallet_app.R
import com.example.nutech_ewallet_app.ViewModelFactory
import com.example.nutech_ewallet_app.databinding.ActivityLoginBinding
import com.example.nutech_ewallet_app.ui.BaseActivity
import com.example.nutech_ewallet_app.ui.registration.RegistrationActivity
import com.example.nutech_ewallet_app.utils.observeOnce

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //FORCE DISABLE NIGHT MODE
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        supportActionBar?.hide()
        binding.loadingLayout.loading.visibility = View.VISIBLE

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

        //LOGIN SESSION CHECKING
        viewModel.token.observeOnce(this) {
            if (it != null && it.isNotEmpty()) {
                val intent = Intent(this, BaseActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                binding.loadingLayout.loading.visibility = View.GONE
            }
        }

        //LOGIN
        binding.btnLogin.setOnClickListener {
            binding.loadingLayout.loading.visibility = View.VISIBLE
            try {
                val email = binding.edtEmail.text.toString()
                val password = binding.edtPassword.text.toString()
                viewModel.login(email, password).observe(this) {
                    binding.loadingLayout.loading.visibility = View.GONE
                    if (it?.status == 0 && it.data != null) {
                        viewModel.setToken(it.data.token!!)
                        val intent = Intent(this, BaseActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        AlertDialog
                            .Builder(this@LoginActivity)
                            .setTitle(it?.message)
                            .setPositiveButton("Back") { listener, _ -> listener.dismiss() }
                            .show()
                    }
                }
            } catch (e: Exception) {
                Log.e("Login", e.message.toString())
            }
        }

        //REGISTRATION
        binding.btnRegistration.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }
}