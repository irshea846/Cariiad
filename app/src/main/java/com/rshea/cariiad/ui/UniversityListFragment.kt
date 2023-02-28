package com.rshea.cariiad.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rshea.cariiad.R
import com.rshea.cariiad.databinding.FragmentUniversityListBinding
import com.rshea.cariiad.models.University
import com.rshea.cariiad.utils.DataState
import com.rshea.cariiad.viewmodels.UniversityViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class UniversityListFragment : Fragment() {

    private lateinit var universityRecyclerAdapter: UniversityRecyclerAdapter
    private var _binding: FragmentUniversityListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val universityViewModel: UniversityViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onCreated()"
        }
        ViewModelProvider(
            this,
            UniversityViewModel.Factory(activity.application)
        )[UniversityViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUniversityListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeObservers()

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_UniversityListFragment_to_UniversityDetailFragment)
        }
    }

    private fun subscribeObservers() {
        universityViewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is DataState.Success<List<University>> -> {
                    displayProgressBar(binding, false)
                    initRecyclerView(binding, uiState.data)
                }
                is DataState.Error -> {
                    displayProgressBar(binding, false)
                    displayError(uiState.exception.message)
                }
                is DataState.Loading -> {
                    displayProgressBar(binding, true)
                }
            }
        }
    }

    private fun initRecyclerView(binding: FragmentUniversityListBinding, universities: List<University>) {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            universityRecyclerAdapter = UniversityRecyclerAdapter(universities)
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
            adapter = universityRecyclerAdapter
        }
    }

    private fun getNetworkTextFromMessage(message: String?): String =
        resources.getText(
            if (message.isNullOrEmpty()) R.string.unknown_error else R.string.network_problem
        ).toString()

    private fun displayError(message: String?) {
        Toast.makeText(this.context, getNetworkTextFromMessage(message), Toast.LENGTH_LONG).show()
    }

    private fun displayProgressBar(binding: FragmentUniversityListBinding, isDisplayed: Boolean){
        binding.progressBar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}