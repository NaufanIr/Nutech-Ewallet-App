package com.example.nutech_ewallet_app.ui.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.nutech_ewallet_app.R
import com.example.nutech_ewallet_app.ViewModelFactory
import com.example.nutech_ewallet_app.databinding.FragmentProfileBinding
import com.example.nutech_ewallet_app.ui.home.HomeViewModel
import com.example.nutech_ewallet_app.ui.login.LoginActivity
import com.example.nutech_ewallet_app.utils.observeOnce

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProfileViewModel
    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private var isEditAble = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        val factory = ViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(this, factory)[ProfileViewModel::class.java]

        setupData()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogout.setOnClickListener { logout() }

        binding.btnEdit.setOnClickListener { editProfile() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupData() {
        firstName = binding.edtFirstName
        lastName = binding.edtLastName
        viewModel.user.observe(requireActivity()) {
            try {
                if (it?.status == 0 && it.data != null) {
                    val user = it.data
                    firstName.setText(user.firstName)
                    lastName.setText(user.lastName)
                    binding.edtEmail.setText(user.email)
                } else {
                    AlertDialog
                        .Builder(requireContext())
                        .setTitle(it?.message)
                        .setPositiveButton("Login") { dialog, _ ->
                            val intent = Intent(requireContext(), LoginActivity::class.java)
                            viewModel.clearToken()
                            startActivity(intent)
                            dialog.dismiss()
                            requireActivity().finish()
                        }
                }
            } catch (e: Exception) {
                Log.e("Profile Fragment", e.message.toString())
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun editProfile() {

        isEditAble = !isEditAble
        if (isEditAble) {
            binding.btnEdit.text = "Save Profile"
            firstName.isEnabled = true
            lastName.isEnabled = true

        } else {
            if (firstName.text.isNullOrEmpty() || lastName.text.isNullOrEmpty()) {
                AlertDialog
                    .Builder(requireContext())
                    .setTitle("Name cannot be empty")
                    .setPositiveButton("Back") { listener, _ -> listener.dismiss() }
                    .show()
            } else {
                viewModel.updateUserProfile(firstName.text.toString(), lastName.text.toString())
                    .observe(viewLifecycleOwner) {
                        if (it?.status == 0 && it.data != null) {
                            firstName.setText(it.data.firstName)
                            lastName.setText(it.data.lastName)
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                                .show()
                            binding.btnEdit.text = "Edit Profile"
                            firstName.isEnabled = false
                            lastName.isEnabled = false
                        } else {
                            AlertDialog
                                .Builder(requireContext())
                                .setTitle(it?.message)
                                .setPositiveButton("Back") { listener, _ -> listener.dismiss() }
                                .show()
                        }
                    }
            }
        }
    }

    private fun logout() {
        viewModel.clearToken()
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

}