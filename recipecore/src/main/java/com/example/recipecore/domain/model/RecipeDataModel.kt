package com.example.recipecore.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.example.recipecore.data.model.AnalyzedInstruction

class RecipeDataModel(
    val imageUrl: String?,
    val title: String?,
    val recipeDesc: String?,
    val noOfLike: Int,
    val duration: Int,
    val veg: Boolean,
    val analyzedInstructions: List<AnalyzedInstruction>?,
    val recipeContent:List<RecipeContent>?,
    val id:Int
    ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.createTypedArrayList(AnalyzedInstruction),
        parcel.createTypedArrayList(RecipeContent),
        parcel.readInt()
    ) {
    }

    override fun equals(other: Any?): Boolean {
        var isSame = true
        val item = other as RecipeDataModel?
        isSame = isSame && this.imageUrl == item?.imageUrl
        isSame = isSame && this.title == item?.title
        isSame = isSame && this.recipeDesc == item?.recipeDesc
        isSame = isSame && this.noOfLike == item?.noOfLike
        isSame = isSame && this.veg == item?.veg
        isSame = isSame && this.duration == item?.duration
        return isSame
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(imageUrl)
        parcel.writeString(title)
        parcel.writeString(recipeDesc)
        parcel.writeInt(noOfLike)
        parcel.writeInt(duration)
        parcel.writeByte(if (veg) 1 else 0)
        parcel.writeTypedList(analyzedInstructions)
        parcel.writeTypedList(recipeContent)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RecipeDataModel> {
        override fun createFromParcel(parcel: Parcel): RecipeDataModel {
            return RecipeDataModel(parcel)
        }

        override fun newArray(size: Int): Array<RecipeDataModel?> {
            return arrayOfNulls(size)
        }
    }
}