package org.example.project.data

import androidx.compose.ui.graphics.Color
import androidx.room.TypeConverter

class ColorTypeConvertor {

    // Convert Color to String
    @TypeConverter
    fun colorToString(color: Color): String {
        val alpha = ((color.alpha * 255).toInt()).toString(16).padStart(2, '0')
        val red = ((color.red * 255).toInt()).toString(16).padStart(2, '0')
        val green = ((color.green * 255).toInt()).toString(16).padStart(2, '0')
        val blue = ((color.blue * 255).toInt()).toString(16).padStart(2, '0')
        return "#$alpha$red$green$blue".uppercase()
    }

    // Convert String to Color
    @TypeConverter
    fun stringToColor(colorString: String): Color {
        val color = colorString.removePrefix("#")
        return when (color.length) {
            6 -> { // RGB format
                val r = color.substring(0, 2).toInt(16)
                val g = color.substring(2, 4).toInt(16)
                val b = color.substring(4, 6).toInt(16)
                Color(r, g, b)
            }
            8 -> { // ARGB format
                val a = color.substring(0, 2).toInt(16)
                val r = color.substring(2, 4).toInt(16)
                val g = color.substring(4, 6).toInt(16)
                val b = color.substring(6, 8).toInt(16)
                Color(r, g, b, a)
            }
            else -> throw IllegalArgumentException("Invalid color format")
        }
    }
}