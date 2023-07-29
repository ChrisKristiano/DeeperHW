package com.example.deeperhw.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.deeperhw.R
import com.example.deeperhw.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: HomeScanAdapter
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentHomeBinding.inflate(inflater, container, false).also {
        setupAdapter()
        it.scanList.adapter = adapter
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEvents()
    }

    private fun setupAdapter() {
        adapter = HomeScanAdapter(emptyList()) { id ->
            id?.let {
                // TODO: Navigate
            } ?: run {
                Toast.makeText(requireContext(), R.string.error, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.scannedLocations.collectLatest {
                        adapter.updateList(it)
                    }
                }

                launch {
                    viewModel.onError.collectLatest {
                        Toast.makeText(requireContext(), R.string.error, Toast.LENGTH_LONG).show()
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
        binding.scanList.isVisible = !isLoading
        binding.loadingIndicator.isVisible = isLoading
    }
}