package com.example.recipecore.domain.model

import android.os.Parcel
import android.os.Parcelable

data class RecipeContent(
    val name:String?,
    val desired:Boolean?
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeValue(desired)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RecipeContent> {
        override fun createFromParcel(parcel: Parcel): RecipeContent {
            return RecipeContent(parcel)
        }

        override fun newArray(size: Int): Array<RecipeContent?> {
            return arrayOfNulls(size)
        }
    }

}
