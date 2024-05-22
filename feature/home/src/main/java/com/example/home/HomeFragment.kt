package com.example.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.home.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by activityViewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val adapter = CitiesAdapter()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("TAG", "onAttach: asddsa") // todo remove
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
    }

    private fun setupListeners() {
        // todo impl search city
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.updateQuery(newText.orEmpty())
                return true
            }
        })
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
                    Log.d("TAG", "onViewCreated: collecting $it") // todo remove
                    binding.searchView.setQuery(it.query, false)
                    adapter.submitList(it.cities)
                }
            }
        }
    }
}