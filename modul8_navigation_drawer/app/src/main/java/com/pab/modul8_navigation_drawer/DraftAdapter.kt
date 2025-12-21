package com.pab.modul8_navigation_drawer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DraftAdapter(private val drafts: List<Draft>) : RecyclerView.Adapter<DraftAdapter.DraftViewHolder>() {

    class DraftViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTo: TextView = itemView.findViewById(R.id.tv_to)
        val tvSubject: TextView = itemView.findViewById(R.id.tv_subject)
        val tvMessage: TextView = itemView.findViewById(R.id.tv_message)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DraftViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_draft, parent, false)
        return DraftViewHolder(view)
    }

    override fun onBindViewHolder(holder: DraftViewHolder, position: Int) {
        val draft = drafts[position]
        holder.tvTo.text = "To: ${draft.to}"
        holder.tvSubject.text = "Subject: ${draft.subject}"
        holder.tvMessage.text = draft.message
    }

    override fun getItemCount(): Int = drafts.size
}
