package com.teamwiney.core_design_system.components

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneNumberVisualTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = text.text.filter { it.isDigit() }
        val formatted = buildString {
            for ((currentIndex, i) in trimmed.indices.withIndex()) {
                if (i == 3 || i == 7) {
                    append('-')
                }
                append(trimmed[currentIndex])
            }
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                val commas = formatted.count { it == '-' }
                return trimmed.length + commas
            }

            override fun transformedToOriginal(offset: Int): Int {
                val commas = formatted.count { it == '-' }
                return trimmed.length
            }
        }

        return TransformedText(
            text = AnnotatedString(formatted),
            offsetMapping = offsetMapping
        )
    }
}