package com.mytheclipse.modul9_tugas.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mytheclipse.modul9_tugas.adapters.ChatAdapter
import com.mytheclipse.modul9_tugas.databinding.FragmentAiChatBinding
import com.mytheclipse.modul9_tugas.models.ChatMessage
import com.mytheclipse.modul9_tugas.network.RetrofitClient
import kotlinx.coroutines.launch

class AiChatFragment : Fragment() {
    
    private var _binding: FragmentAiChatBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var chatAdapter: ChatAdapter
    private var currentSession: String? = null
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAiChatBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        setupClickListeners()
    }
    
    private fun setupRecyclerView() {
        chatAdapter = ChatAdapter()
        binding.rvChat.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = chatAdapter
        }
    }
    
    private fun setupClickListeners() {
        binding.btnSend.setOnClickListener {
            val message = binding.etMessage.text.toString().trim()
            if (message.isNotEmpty()) {
                sendMessage(message)
            } else {
                Toast.makeText(requireContext(), "Masukkan pesan terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
        }
        
        binding.btnClearChat.setOnClickListener {
            chatAdapter.clearMessages()
            currentSession = null
            Toast.makeText(requireContext(), "Chat dibersihkan", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun sendMessage(message: String) {
        // Add user message to chat
        chatAdapter.addMessage(ChatMessage(message, isUser = true))
        binding.etMessage.text?.clear()
        binding.rvChat.scrollToPosition(chatAdapter.itemCount - 1)
        
        // Show loading
        binding.progressBar.visibility = View.VISIBLE
        binding.btnSend.isEnabled = false
        
        lifecycleScope.launch {
            try {
                val selectedAi = when (binding.rgAiModel.checkedRadioButtonId) {
                    binding.rbGemini.id -> "gemini"
                    else -> "chatgpt"
                }
                
                val response = if (selectedAi == "gemini") {
                    RetrofitClient.apiService.gemini(message)
                } else {
                    RetrofitClient.apiService.chatGpt(
                        text = message,
                        session = currentSession
                    )
                }
                
                if (response.isSuccessful) {
                    val aiResponse = response.body()
                    aiResponse?.let {
                        val aiMessage = it.result ?: "Maaf, tidak ada respons"
                        chatAdapter.addMessage(ChatMessage(aiMessage, isUser = false))
                        binding.rvChat.scrollToPosition(chatAdapter.itemCount - 1)
                        
                        // Save session for ChatGPT
                        if (selectedAi == "chatgpt") {
                            currentSession = it.session
                        }
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
                    Toast.LENGTH_SHORT
                ).show()
            } finally {
                binding.progressBar.visibility = View.GONE
                binding.btnSend.isEnabled = true
            }
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
