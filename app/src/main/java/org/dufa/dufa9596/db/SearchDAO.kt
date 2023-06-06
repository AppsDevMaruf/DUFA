package org.dufa.dufa9596.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import org.dufa.dufa9596.db.room.*

@Dao
interface SearchDAO {

    @Query("SELECT * FROM BloodGroup")
    suspend fun getAllBloodGroup(): MutableList<BloodGroup>

    @Insert
    fun insertDepartment(department: org.dufa.dufa9596.db.room.Department_java)


    @Query("SELECT * FROM Department")
    suspend fun getAllDepartment(): MutableList<Department>

    @Query("SELECT * FROM DISTRICT")
    suspend fun getAllDistrict(): MutableList<District>

    @Query("SELECT * FROM Profession")
    suspend fun getAllProfession(): MutableList<Profession>
}