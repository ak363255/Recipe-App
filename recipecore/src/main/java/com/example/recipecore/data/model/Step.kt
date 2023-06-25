package com.example.recipecore.data.model

import android.os.Parcel
import android.os.Parcelable

data class Step(
    val equipment: List<Equipment>?,
    val ingredients: List<Ingredient>?,
    val number: Int,
    val step: String?
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(Equipment),
        parcel.createTypedArrayList(Ingredient),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(equipment)
        parcel.writeTypedList(ingredients)
        parcel.writeInt(number)
        parcel.writeString(step)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Step> {
        override fun createFromParcel(parcel: Parcel): Step {
            return Step(parcel)
        }

        override fun newArray(size: Int): Array<Step?> {
            return arrayOfNulls(size)
        }
    }


}