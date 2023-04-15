package com.marufalam.dufa.db.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BloodGroup(
    @PrimaryKey(autoGenerate = true ) val uid: Int,
    @ColumnInfo val bloodGroup: String

    )
