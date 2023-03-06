package com.rshea.cariiad.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rshea.cariiad.R
import com.rshea.cariiad.databinding.FragmentUniversityDetailBinding
import com.rshea.cariiad.databinding.FragmentUniversityListBinding
import com.rshea.cariiad.models.University
import com.rshea.cariiad.utils.DataState
import com.rshea.cariiad.viewmodels.UniversityViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class UniversityDetailFragment : Fragment() {

    private var _binding: FragmentUniversityDetailBinding? = null
    private val universityViewModel: UniversityViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onCreated()"
        }
        ViewModelProvider(
            this,
            UniversityViewModel.Factory(activity.application)
        )[UniversityViewModel::class.java]
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var position: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUniversityDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        position = arguments?.getInt("position")
        position?.let {
            universityViewModel.fetchUniversityInfo(it)
            subscribeObservers()
        }
    }

    private fun subscribeObservers() {
        universityViewModel.uiOneState.observe(viewLifecycleOwner) { uiOneState ->
            when (uiOneState) {
                is DataState.Success<University> -> {
                    bindView(binding, uiOneState.data)
                }
                is DataState.Error -> {
                    displayError(uiOneState.exception.message)
                }
                is DataState.Loading -> {
                    //Toast.makeText(this.context, "Data Loading", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun bindView(binding: FragmentUniversityDetailBinding, data: University) {
        binding.universityName.text = data.name
        binding.universityCountry.text = data.country
        binding.universityWebpage.text = data.webPagesList[0]
    }

    private fun getNetworkTextFromMessage(message: String?): String =
        resources.getText(
            if (message.isNullOrEmpty()) R.string.unknown_error else R.string.network_problem
        ).toString()


    private fun displayError(message: String?) {
        Toast.makeText(this.context, getNetworkTextFromMessage(message), Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

 }