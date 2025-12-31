package com.mytheclipse.modul9_tugas.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.mytheclipse.modul9_tugas.databinding.FragmentDownloaderBinding
import com.mytheclipse.modul9_tugas.network.RetrofitClient
import kotlinx.coroutines.launch

class DownloaderFragment : Fragment() {
    
    private var _binding: FragmentDownloaderBinding? = null
    private val binding get() = _binding!!
    
    private var downloadUrl: String? = null
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDownloaderBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupClickListeners()
    }
    
    private fun setupClickListeners() {
        binding.btnDownload.setOnClickListener {
            val url = binding.etUrl.text.toString().trim()
            if (url.isNotEmpty()) {
                processDownload(url)
            } else {
                Toast.makeText(requireContext(), "Masukkan URL terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
        }
        
        binding.btnOpenLink.setOnClickListener {
            downloadUrl?.let { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
        }
    }
    
    private fun processDownload(url: String) {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnDownload.isEnabled = false
        binding.cardResult.visibility = View.GONE
        
        lifecycleScope.launch {
            try {
                val platform = detectPlatform(url)
                val response = when (platform) {
                    "youtube" -> RetrofitClient.apiService.youtubeDownload(url)
                    "facebook" -> RetrofitClient.apiService.facebookDownload(url)
                    "tiktok" -> RetrofitClient.apiService.tiktokDownload(url)
                    else -> {
                        Toast.makeText(requireContext(), "Platform tidak didukung", Toast.LENGTH_SHORT).show()
                        return@launch
                    }
                }
                
                if (response.isSuccessful) {
                    val data = response.body()
                    data?.let {
                        binding.tvTitle.text = it.title ?: "Tidak ada judul"
                        binding.tvQuality.text = "Quality: ${it.quality ?: "N/A"}"
                        
                        // Load thumbnail
                        it.thumbnail?.let { thumb ->
                            Glide.with(requireContext())
                                .load(thumb)
                                .into(binding.ivThumbnail)
                        }
                        
                        // Get download URL
                        downloadUrl = it.url ?: it.download?.firstOrNull()?.url
                        
                        binding.cardResult.visibility = View.VISIBLE
                        binding.btnOpenLink.isEnabled = downloadUrl != null
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Error: ${response.code()} - ${response.message()}",
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
                binding.btnDownload.isEnabled = true
            }
        }
    }
    
    private fun detectPlatform(url: String): String {
        return when {
            url.contains("youtube.com") || url.contains("youtu.be") -> "youtube"
            url.contains("facebook.com") || url.contains("fb.watch") -> "facebook"
            url.contains("tiktok.com") -> "tiktok"
            else -> "unknown"
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
