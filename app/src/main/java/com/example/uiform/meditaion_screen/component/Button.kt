package com.example.uiform.meditaion_screen.component

import com.example.uiform.R
import com.example.uiform.util.TimesOfDay


fun validateMeditation(
    name : String,
    dosage : Int,
    endDate : Long,
    recurrence : String,
    morningSelection : Boolean,
    afternoonSelection : Boolean,
    eveningSelection : Boolean,
    nightSelection : Boolean,
    onInvalidate : (Int) -> Unit,
    onValidate : () -> Unit
) {
    if (name.isEmpty()){
        onInvalidate(R.string.medication_name)
        return
    }

    if(dosage < 1 ){
        onInvalidate(R.string.dosage)
        return
    }

    if (endDate < 1){
        onInvalidate(R.string.end_Date)
        return
    }

    if(!morningSelection && !afternoonSelection && !eveningSelection && !nightSelection){
        onInvalidate(R.string.time_of_day)
        return
    }

    val timeOfDay = mutableListOf<TimesOfDay>()
    if (morningSelection) timeOfDay.add(TimesOfDay.Morning)
    if (afternoonSelection) timeOfDay.add(TimesOfDay.Afternoon)
    if (eveningSelection) timeOfDay.add(TimesOfDay.Evening)
    if (nightSelection) timeOfDay.add(TimesOfDay.Night)

    onValidate()




}

