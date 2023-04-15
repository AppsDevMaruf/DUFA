package com.marufalam.dufa.interfaces

import com.marufalam.dufa.data.models.get_departments.Department


interface DepartmentSelectListener {

    fun selectedDepartment( departments: Department)

}