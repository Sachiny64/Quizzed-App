package com.myfirstapp.quizzed.ulils

object ColorPicker {
    val colors= arrayOf(
        "#3EB9DE",
        "#3685BC",
        "#D36280",
        "#E44F55",
        "#FA8056",
        "#818BCA",
        "#7D659F"
    )

    var currentColorIndex=0
    fun getColor() : String {
        currentColorIndex= (currentColorIndex + 1) % colors.size
        return colors[currentColorIndex]

    }
}