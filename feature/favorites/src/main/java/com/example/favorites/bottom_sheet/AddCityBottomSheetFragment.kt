package com.example.favorites.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.favorites.databinding.AddCityBottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AddCityBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: AddCityBottomSheetDialogBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<AddCityViewModel>()
    private lateinit var adapter: CitiesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddCityBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CitiesAdapter {
            viewModel.markCity(it)
        }
        binding.rvFavoriteCities.adapter = adapter
        binding.rvFavoriteCities.layoutManager = LinearLayoutManager(requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                adapter.submitList(state.filteredCities)
            }
        }

        binding.etCityName.addTextChangedListener {
            viewModel.updateQuery(binding.etCityName.text.toString())
        }
    }
}