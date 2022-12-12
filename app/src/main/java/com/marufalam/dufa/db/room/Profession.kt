package com.marufalam.dufa.db.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Profession(
    @PrimaryKey(autoGenerate = true ) val uid: Int,
    @ColumnInfo val profession: String

    )
