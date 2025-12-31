package com.mytheclipse.modul9_tugas.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.mytheclipse.modul9_tugas.databinding.FragmentImageToolsBinding
import com.mytheclipse.modul9_tugas.network.RetrofitClient
import kotlinx.coroutines.launch

class ImageToolsFragment : Fragment() {
    
    private var _binding: FragmentImageToolsBinding? = null
    private val binding get() = _binding!!
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageToolsBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupClickListeners()
    }
    
    private fun setupClickListeners() {
        binding.btnGenerate.setOnClickListener {
            val text = binding.etText.text.toString().trim()
            if (text.isNotEmpty()) {
                generateImage(text)
            } else {
                Toast.makeText(requireContext(), "Masukkan teks terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun generateImage(text: String) {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnGenerate.isEnabled = false
        binding.cardResult.visibility = View.GONE
        
        lifecycleScope.launch {
            try {
                val imageType = when (binding.rgImageType.checkedRadioButtonId) {
                    binding.rbQuotly.id -> "quotly"
                    else -> "brat"
                }
                
                val response = if (imageType == "brat") {
                    RetrofitClient.apiService.bratImage(text)
                } else {
                    RetrofitClient.apiService.quotlyImage(
                        text = text,
                        username = "User"
                    )
                }
                
                if (response.isSuccessful) {
                    val data = response.body()
                    val imageUrl = data?.url ?: data?.image
                    
                    if (imageUrl != null) {
                        Glide.with(requireContext())
                            .load(imageUrl)
                            .into(binding.ivGeneratedImage)
                        
                        binding.cardResult.visibility = View.VISIBLE
                        Toast.makeText(requireContext(), "Gambar berhasil dibuat!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Tidak ada gambar yang dihasilkan", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Error: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    "Error: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            } finally {
                binding.progressBar.visibility = View.GONE
                binding.btnGenerate.isEnabled = true
            }
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
