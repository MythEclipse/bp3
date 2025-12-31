package com.mytheclipse.modul9_tugas.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.mytheclipse.modul9_tugas.databinding.FragmentSearchBinding
import com.mytheclipse.modul9_tugas.network.RetrofitClient
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupClickListeners()
    }
    
    private fun setupClickListeners() {
        binding.btnSearch.setOnClickListener {
            val query = binding.etQuery.text.toString().trim()
            if (query.isNotEmpty()) {
                performSearch(query)
            } else {
                Toast.makeText(requireContext(), "Masukkan query terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun performSearch(query: String) {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnSearch.isEnabled = false
        binding.cardResult.visibility = View.GONE
        
        lifecycleScope.launch {
            try {
                val searchType = when (binding.rgSearchType.checkedRadioButtonId) {
                    binding.rbGithub.id -> "github"
                    else -> "google"
                }
                
                if (searchType == "github") {
                    searchGithub(query)
                } else {
                    searchGoogle(query)
                }
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    "Error: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
                binding.progressBar.visibility = View.GONE
                binding.btnSearch.isEnabled = true
            }
        }
    }
    
    private suspend fun searchGithub(username: String) {
        try {
            val response = RetrofitClient.apiService.githubStalk(username)
            
            if (response.isSuccessful) {
                val profile = response.body()
                profile?.let {
                    binding.tvResultTitle.text = it.name ?: it.login
                    binding.tvResultDescription.text = buildString {
                        append("Username: ${it.login}\n")
                        append("Bio: ${it.bio ?: "Tidak ada bio"}\n")
                        append("Followers: ${it.followers ?: 0}\n")
                        append("Following: ${it.following ?: 0}\n")
                        append("Public Repos: ${it.publicRepos ?: 0}")
                    }
                    
                    it.avatarUrl?.let { avatar ->
                        Glide.with(requireContext())
                            .load(avatar)
                            .circleCrop()
                            .into(binding.ivResultImage)
                    }
                    
                    binding.cardResult.visibility = View.VISIBLE
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "User tidak ditemukan",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } finally {
            binding.progressBar.visibility = View.GONE
            binding.btnSearch.isEnabled = true
        }
    }
    
    private suspend fun searchGoogle(query: String) {
        try {
            val response = RetrofitClient.apiService.googleSearch(query)
            
            if (response.isSuccessful) {
                val searchResponse = response.body()
                val results = searchResponse?.results
                
                if (!results.isNullOrEmpty()) {
                    val firstResult = results[0]
                    binding.tvResultTitle.text = firstResult.title ?: "Tidak ada judul"
                    binding.tvResultDescription.text = buildString {
                        append("${firstResult.description ?: "Tidak ada deskripsi"}\n\n")
                        append("URL: ${firstResult.url ?: ""}\n\n")
                        append("Total hasil: ${results.size}")
                    }
                    binding.ivResultImage.visibility = View.GONE
                    binding.cardResult.visibility = View.VISIBLE
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Tidak ada hasil ditemukan",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Error: ${response.code()}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } finally {
            binding.progressBar.visibility = View.GONE
            binding.btnSearch.isEnabled = true
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
