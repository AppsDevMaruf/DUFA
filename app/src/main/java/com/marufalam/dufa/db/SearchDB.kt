package com.marufalam.dufa.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.marufalam.dufa.db.room.BloodGroup
import com.marufalam.dufa.db.room.Department
import com.marufalam.dufa.db.room.District
import com.marufalam.dufa.db.room.Profession

@Database(
    entities = [Department::class, District::class, BloodGroup::class, Profession::class],
    version = 1
)
abstract class SearchDB : RoomDatabase() {

    public abstract fun getSearchDao(): SearchDAO

    companion object {
        fun getDB(context: Context): SearchDB {
            var db: SearchDB? = null

            return if (db == null) {
                db = Room.databaseBuilder(context, SearchDB::class.java, "search_db").build()
                db
            } else {
                db
            }
        }


    }


}