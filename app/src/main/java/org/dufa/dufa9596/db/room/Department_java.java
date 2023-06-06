package org.dufa.dufa9596.db.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Department_java {
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo
    String dep_name;

    public Department_java(String dep_name) {
        this.dep_name = dep_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDep_name() {
        return dep_name;
    }

    public void setDep_name(String dep_name) {
        this.dep_name = dep_name;
    }
}
