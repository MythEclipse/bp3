package com.pab.modul8_navigation_drawer

data class Draft(
    val to: String,
    val subject: String,
    val message: String
)

object DraftRepository {
    val drafts = mutableListOf<Draft>()
}
