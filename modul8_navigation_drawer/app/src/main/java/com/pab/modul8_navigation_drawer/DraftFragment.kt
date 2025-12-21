package com.pab.modul8_navigation_drawer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DraftFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_draft, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnGoToActivity: Button = view.findViewById(R.id.btn_go_to_activity)
        btnGoToActivity.setOnClickListener {
            val intent = Intent(requireContext(), DraftActivity::class.java)
            startActivity(intent)
        }

        recyclerView = view.findViewById(R.id.rv_drafts)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        updateDrafts()
    }

    override fun onResume() {
        super.onResume()
        updateDrafts()
    }

    private fun updateDrafts() {
        val drafts = DraftRepository.drafts
        if (drafts.isEmpty()) {
            recyclerView.visibility = View.GONE
            view?.findViewById<TextView>(R.id.tv_empty)?.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            view?.findViewById<TextView>(R.id.tv_empty)?.visibility = View.GONE
            recyclerView.adapter = DraftAdapter(drafts)
        }
    }
}
