package com.leopold95.moviebrowser.shared

object ReleaseDateFormatter {
    private val yearRegex = Regex("^(\\d{4})")

    fun extractYear(releaseDate: String?): String {
        if (releaseDate.isNullOrBlank()) return ""
        return yearRegex.find(releaseDate)?.groupValues?.get(1) ?: ""
    }
}

