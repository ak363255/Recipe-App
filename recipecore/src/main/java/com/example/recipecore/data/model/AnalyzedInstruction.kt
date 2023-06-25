package com.example.recipecore.data.model

import android.os.Parcel
import android.os.Parcelable

data class AnalyzedInstruction(
    val name: String?,
    val steps: List<Step>?
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.createTypedArrayList(Step)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeTypedList(steps)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AnalyzedInstruction> {
        override fun createFromParcel(parcel: Parcel): AnalyzedInstruction {
            return AnalyzedInstruction(parcel)
        }

        override fun newArray(size: Int): Array<AnalyzedInstruction?> {
            return arrayOfNulls(size)
        }
    }


}