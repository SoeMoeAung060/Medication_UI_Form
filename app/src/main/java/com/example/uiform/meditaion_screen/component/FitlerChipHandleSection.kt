package com.example.uiform.meditaion_screen.component

import android.content.Context
import android.widget.Toast

fun handleSelection(
    isSelected: Boolean,
    selectionCount: Int,
    onStateChange: (Int, Boolean) -> Unit,
    canSelectMoreTimeOfDay: Boolean,
    onShowMaxSelectionError: () -> Unit
) {
    if (isSelected) {
        onStateChange(selectionCount - 1, !isSelected)
    } else if (canSelectMoreTimeOfDay) {
        onStateChange(selectionCount + 1, !isSelected)
    } else {
        onShowMaxSelectionError()
    }
}

fun canSelectMoreTimeOfDay(selectionCount: Int, numberOfDosage: Int): Boolean {
    return selectionCount < numberOfDosage
}

fun onShowMaxSelectionToast(
    numberOfDosage: String,
    context: Context
) {
    Toast.makeText(
        context,
        "Your are selecting ${(numberOfDosage.toIntOrNull() ?: 0) + 1} times of days which is more than number of dosage.",
        Toast.LENGTH_LONG
    ).show()
}
