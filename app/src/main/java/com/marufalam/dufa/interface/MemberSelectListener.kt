package com.marufalam.dufa.`interface`

import com.marufalam.dufa.data.models.dashboard.ResponseAllMember

interface MemberSelectListener {
    fun selectedMember( responseDetail: ResponseAllMember)
}