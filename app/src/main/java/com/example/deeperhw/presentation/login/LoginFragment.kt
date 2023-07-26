package com.example.deeperhw.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.deeperhw.R
import com.example.deeperhw.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentLoginBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupEvents()
    }

    private fun setupListeners() {
        binding.buttonLogin.setOnClickListener {
            viewModel.login(
                binding.emailText.text.toString(),
                binding.passwordText.text.toString()
            )
        }
    }

    private fun setupEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.onLoginSuccess.collectLatest {
                        // TODO: Navigate to next step
                    }
                }

                launch {
                    viewModel.onLoginError.collectLatest {
                        // TODO: Show error
                    }
                }

                launch {
                    viewModel.isLoading.collectLatest {
                        setLoading(it)
                    }
                }
            }
        }
    }

    private fun setLoading(isLoading: Boolean) {
        binding.buttonLogin.isVisible = !isLoading
        binding.loadingIndicator.isVisible = isLoading
    }
}
