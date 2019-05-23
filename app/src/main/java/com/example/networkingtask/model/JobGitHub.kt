package com.example.networkingtask.model

import com.google.gson.annotations.SerializedName

/**
 * Data class for displaying data in Recycler View.
 *
 * @author Alexander Gorin
 */
data class JobGitHub(
    @SerializedName("company") val company: String,
    @SerializedName("location") val location: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String
)