package com.myfirstapp.quizzed.ulils

import com.myfirstapp.quizzed.R

object IconPicker {
    val icons= arrayOf(
        R.drawable.ic_icon_2,
        R.drawable.ic_icon_3,
        R.drawable.ic_icon_4,
        R.drawable.ic_icon_5,
        R.drawable.ic_icon_6,


    )

    var currentIcon=0
    fun getIcon() : Int {
        currentIcon= (currentIcon + 1) % icons.size
        return icons[currentIcon]

    }
}