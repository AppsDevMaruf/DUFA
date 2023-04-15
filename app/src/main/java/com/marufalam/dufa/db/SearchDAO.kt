package com.marufalam.dufa.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.marufalam.dufa.db.room.*
import javax.inject.Inject

@Dao
interface SearchDAO {

    @Query("SELECT * FROM BloodGroup")
    suspend fun getAllBloodGroup(): MutableList<BloodGroup>

    @Insert
    fun insertDepartment(department: Department_java)


    @Query("SELECT * FROM Department")
    suspend fun getAllDepartment(): MutableList<Department>

    @Query("SELECT * FROM DISTRICT")
    suspend fun getAllDistrict(): MutableList<District>

    @Query("SELECT * FROM Profession")
    suspend fun getAllProfession(): MutableList<Profession>
}