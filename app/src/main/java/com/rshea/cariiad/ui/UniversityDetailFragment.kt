package com.rshea.cariiad.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rshea.cariiad.databinding.FragmentUniversityDetailBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class UniversityDetailFragment : Fragment() {

    private var _binding: FragmentUniversityDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var name: String? = null
    private var country: String? = null
    private var url: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUniversityDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        name = arguments?.getString("name")
        name?.let {
            binding.universityName.text = name
        }
        country = arguments?.getString("country")
        country?.let {
            binding.universityCountry.text = country
        }
        url = arguments?.getString("domain")
        url?.let {
            binding.universityWebpage.text = url
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

 }