package com.jihan.agecalculator.presentation.flipper.enum

enum class CardFace(val angel: Float) {

    Front(0F) {
        override val whatNext: CardFace
            get() = Back

    },
    Back(180F) {
        override val whatNext: CardFace
            get() = Front

    };

    abstract val whatNext: CardFace
}