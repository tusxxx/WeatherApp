package com.example.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.home.adapters.CitiesAdapter
import com.example.home.adapters.FoundCitiesAdapter
import com.example.home.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.Calendar


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by activityViewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val adapter = CitiesAdapter()
    private val foundCitiesAdapter = FoundCitiesAdapter {
        viewModel.toggleCity(it)
        viewModel.updateQuery("")
        binding.searchView.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupListeners()
        setupCollecting()

    }

    private fun setupViews() {
        binding.rvCitiesWeather.apply {
            adapter = this@HomeFragment.adapter
            layoutManager = LinearLayoutManager(this@HomeFragment.requireContext())
        }
        binding.rvFilteredCities.apply {
            adapter = foundCitiesAdapter
            layoutManager = LinearLayoutManager(this@HomeFragment.requireContext())
        }
        val today = Calendar.getInstance().time
        val todayStr = DateFormat.getDateInstance(DateFormat.MEDIUM).format(today)
        binding.tvTitle.text = "Hello. Today is $todayStr"
    }

    private fun setupListeners() {
        binding.searchView.editText.addTextChangedListener {
            viewModel.updateQuery(it?.toString().orEmpty())
        }
    }

    private fun setupCollecting() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    if (it.isLoading) {
                        binding.progressIndicator.show()
                    } else {
                        binding.progressIndicator.hide()
                    }
                    binding.searchBar.setText(it.query)
                    adapter.submitList(it.cities)
                    foundCitiesAdapter.submitList(it.filteredCityNames)
                }
            }
        }
    }
}