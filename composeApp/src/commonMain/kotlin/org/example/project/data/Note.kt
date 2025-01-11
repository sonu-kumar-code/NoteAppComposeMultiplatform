package org.example.project.data

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.example.project.Purple_500
import org.example.project.aquamarine
import org.example.project.black
import org.example.project.blue
import org.example.project.brown
import org.example.project.cyan
import org.example.project.dark_blue
import org.example.project.gray
import org.example.project.green
import org.example.project.light_blue
import org.example.project.lime
import org.example.project.magenta
import org.example.project.maroon
import org.example.project.olive
import org.example.project.orange
import org.example.project.pink
import org.example.project.purple
import org.example.project.red
import org.example.project.silver
import org.example.project.white
import org.example.project.yellow

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 1,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Color,
    val textColor: Color,
    val desColor:Color,
    val deleteColor:Color
) {
    companion object {
        val colors = listOf(
            ColorModel(backgroundColor = Purple_500, contentColor = white, titleColor = red),
            ColorModel(backgroundColor = red, contentColor = white, titleColor = blue),
            ColorModel(backgroundColor = cyan, contentColor = white, titleColor = red),
            ColorModel(backgroundColor = blue, contentColor = white, titleColor = red),
            ColorModel(backgroundColor = dark_blue, contentColor = white, titleColor = red),
            ColorModel(backgroundColor = light_blue, contentColor = white, titleColor = red),
            ColorModel(backgroundColor = purple, contentColor = white, titleColor = red),
            ColorModel(backgroundColor = yellow, contentColor = white, titleColor = red),
            ColorModel(backgroundColor = lime, contentColor = white, titleColor = red),
            ColorModel(backgroundColor = magenta, contentColor = white, titleColor = red),
            ColorModel(backgroundColor = pink, contentColor = white, titleColor = red),
            ColorModel(backgroundColor = silver, contentColor = white, titleColor = red),
            ColorModel(backgroundColor = gray, contentColor = white, titleColor = red),
            ColorModel(backgroundColor = orange, contentColor = black, titleColor = red),
            ColorModel(backgroundColor = brown, contentColor = white, titleColor = red),
            ColorModel(backgroundColor = maroon, contentColor = white, titleColor = red),
            ColorModel(backgroundColor = green, contentColor = red, titleColor = red),
            ColorModel(backgroundColor = olive, contentColor = white, titleColor = red),
            ColorModel(backgroundColor = aquamarine, contentColor = white, titleColor = red),
        )
    }

    data class ColorModel(
        val titleColor: Color,
        val contentColor: Color,
        val backgroundColor: Color,
        val deleteIconColor: Color = Color(0xFFFFFFFF)
    )
}

class InvalidNoteException(message: String) : Exception(message)