package com.example.uiform.meditaion_screen.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import java.util.Date
import androidx.compose.material3.FilterChip
import com.example.uiform.R
import com.example.uiform.meditaion_screen.component.EndDateTextField
import com.example.uiform.meditaion_screen.component.RecurrenceDropdownMenu
import com.example.uiform.meditaion_screen.component.canSelectMoreTimeOfDay
import com.example.uiform.meditaion_screen.component.handleSelection
import com.example.uiform.meditaion_screen.component.onShowMaxSelectionToast
import com.example.uiform.meditaion_screen.component.validateMeditation
import com.example.uiform.util.Recurrence
import com.example.uiform.util.TimesOfDay

@SuppressLint("StringFormatInvalid")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMediationScreen() {

    var numberOfDosage by rememberSaveable { mutableStateOf("1") }
    var medicationName by rememberSaveable { mutableStateOf("") }
    var recurrence by rememberSaveable { mutableStateOf(Recurrence.Daily.name) }
    var isMaxDoseError by remember { mutableStateOf(false) }
    var endDate by rememberSaveable { mutableStateOf(Date().time) }

    val context = LocalContext.current

    var isMorningSelected by rememberSaveable { mutableStateOf(false) }
    var isAfternoonSelected by rememberSaveable { mutableStateOf(false) }
    var isEveningSelected by rememberSaveable { mutableStateOf(false) }
    var isNightSelected by rememberSaveable { mutableStateOf(false) }
    var selectionCount by rememberSaveable { mutableStateOf(0) }


    Column(
        modifier = Modifier
            .padding(16.dp, 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.add_mediation),
            style = TextStyle(
                fontSize = MaterialTheme.typography.displaySmall.fontSize,
                fontWeight = FontWeight.Medium
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(id = R.string.medication_name),
            style = TextStyle(fontSize = MaterialTheme.typography.titleMedium.fontSize)
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = medicationName,
            onValueChange = { medicationName = it },
            placeholder = { Text(text = "e.g. Paracetamol") }
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.dosage),
                    style = TextStyle(fontSize = MaterialTheme.typography.titleMedium.fontSize)
                )
                TextField(
                    modifier = Modifier.width(128.dp),
                    value = numberOfDosage,
                    onValueChange = {
                        val maxDose = 3
                        if (it.length < maxDose) {
                            isMaxDoseError = false
                            numberOfDosage = it
                        } else {
                            isMaxDoseError = true
                        }
                    },
                    trailingIcon = {
                        if (isMaxDoseError) {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = "tint"
                            )
                        }
                    },
                    placeholder = { Text(text = "e.g. 1") },
                    isError = isMaxDoseError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
            RecurrenceDropdownMenu { recurrence = it }
        }

        if (isMaxDoseError) {
            Text(
                text = "you can not more than 2 digit per day",
                style = TextStyle(
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    color = MaterialTheme.colorScheme.error
                )
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        EndDateTextField {
            endDate = it
        }

        Spacer(modifier = Modifier.height(4.dp))


        Text(
            text = stringResource(id = R.string.time_of_day),
            style = TextStyle(fontSize = MaterialTheme.typography.titleMedium.fontSize)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                selected = isMorningSelected,
                onClick = {
                    handleSelection(
                        isSelected = isMorningSelected,
                        selectionCount = selectionCount,
                        canSelectMoreTimeOfDay = canSelectMoreTimeOfDay(
                            selectionCount, numberOfDosage.toIntOrNull() ?: 0
                        ),
                        onStateChange = { count, selected ->
                            isMorningSelected = selected
                            selectionCount = count
                        },
                        onShowMaxSelectionError = {
                            onShowMaxSelectionToast(
                                numberOfDosage = numberOfDosage,
                                context = context
                            )
                        }

                    )
                },
                label = { Text(text = TimesOfDay.Morning.name) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Selected"
                    )
                }
            )


            FilterChip(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                selected = isAfternoonSelected,
                onClick = {
                    handleSelection(
                        isSelected = isAfternoonSelected,
                        selectionCount = selectionCount,
                        canSelectMoreTimeOfDay = canSelectMoreTimeOfDay(
                            selectionCount, numberOfDosage.toIntOrNull() ?: 0
                        ),
                        onStateChange = { count, selected ->
                            isAfternoonSelected = selected
                            selectionCount = count
                        },
                        onShowMaxSelectionError = {
                            onShowMaxSelectionToast(
                                numberOfDosage = numberOfDosage,
                                context = context
                            )
                        }

                    )
                },
                label = { Text(text = TimesOfDay.Afternoon.name) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Selected"
                    )
                }
            )
        }


        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                selected = isEveningSelected,
                onClick = {
                    handleSelection(
                        isSelected = isEveningSelected,
                        selectionCount = selectionCount,
                        canSelectMoreTimeOfDay = canSelectMoreTimeOfDay(
                            selectionCount, numberOfDosage.toIntOrNull() ?: 0
                        ),
                        onStateChange = { count, selected ->
                            isEveningSelected = selected
                            selectionCount = count
                        },
                        onShowMaxSelectionError = {
                            onShowMaxSelectionToast(
                                numberOfDosage = numberOfDosage,
                                context = context
                            )
                        }

                    )
                },
                label = { Text(text = TimesOfDay.Evening.name) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Selected"
                    )
                }
            )


            FilterChip(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                selected = isNightSelected,
                onClick = {
                    handleSelection(
                        isSelected = isNightSelected,
                        selectionCount = selectionCount,
                        canSelectMoreTimeOfDay = canSelectMoreTimeOfDay(
                            selectionCount, numberOfDosage.toIntOrNull() ?: 0
                        ),
                        onStateChange = { count, selected ->
                            isNightSelected = selected
                            selectionCount = count
                        },
                        onShowMaxSelectionError = {
                            onShowMaxSelectionToast(
                                numberOfDosage = numberOfDosage,
                                context = context
                            )
                        }

                    )
                },
                label = { Text(text = TimesOfDay.Night.name) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Selected"
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(4.dp))


        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            onClick = {
                validateMeditation(
                    name = medicationName,
                    dosage = numberOfDosage.toIntOrNull() ?: 0,
                    endDate = endDate,
                    recurrence = recurrence,
                    morningSelection = isMorningSelected,
                    afternoonSelection = isAfternoonSelected,
                    eveningSelection = isEveningSelected,
                    nightSelection = isNightSelected,
                    onInvalidate = {
                        Toast.makeText(
                            context,
                            context.getString(R.string.value_is_empty, context.getString(it)),
                            Toast.LENGTH_LONG
                        ).show()
                    },
                    onValidate = {
                        Toast.makeText(
                            context,
                            context.getString(R.string.success),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                )
            }) {
            Text(
                text = stringResource(id = R.string.save),
                style = TextStyle(fontSize = MaterialTheme.typography.bodyLarge.fontSize)
            )
        }

    }
}

@Composable
@Preview(showBackground = true)
fun AddMedicationScreenPreview() {
    AddMediationScreen()
}

