package com.abdi.dicodingeventapp.response

data class DetailEventResponse(
    val id: Int,
    val name: String,
    val description: String,
    val mediaCover: String,
    val ownerName: String,
    val quota: Int,
    val registrants: Int,
    val beginTime: String,
    val endTime: String,
    val link: String
)
