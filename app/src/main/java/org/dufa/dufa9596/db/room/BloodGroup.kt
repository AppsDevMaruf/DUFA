package org.dufa.dufa9596.db.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BloodGroup(
    @PrimaryKey(autoGenerate = true ) val uid: Int,
    @ColumnInfo val bloodGroup: String

    )
