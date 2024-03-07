package com.android.my_app_music.data.model

import android.os.Parcel
import android.os.Parcelable

data class Song(
    val id: Int = 0,
    val songName: String? = "",
    val singerName: String? = "",
    val imageSong: String? = "",
    val songUrl: String? = "",
    val album: Int? = 0,
    val playlist: Int? = 0,
    val category: Int? = 0,
    val like: Boolean = true
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(songName)
        parcel.writeString(singerName)
        parcel.writeString(imageSong)
        parcel.writeString(songUrl)
        parcel.writeValue(album)
        parcel.writeValue(playlist)
        parcel.writeValue(category)
        parcel.writeByte(if (like) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Song> {
        override fun createFromParcel(parcel: Parcel): Song {
            return Song(parcel)
        }

        override fun newArray(size: Int): Array<Song?> {
            return arrayOfNulls(size)
        }
    }
}
