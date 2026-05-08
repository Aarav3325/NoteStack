package com.aarav.notesapp.ui.theme

enum class ThemeMode(val key: String, val label: String) {
    SYSTEM("system", "System"),
    LIGHT("light", "Light"),
    DARK("dark", "Dark");

    companion object {
        fun fromKey(key: String): ThemeMode {
            return entries.find { it.key == key } ?: SYSTEM
        }
    }
}
