package org.dufa.dufa9596.db.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Department(
    @field:ColumnInfo var dep_name: String

) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}
