package com.marufalam.dufa.`interface`

import com.marufalam.dufa.models.dashboard.ResponseMemberList

interface MemberSelectListener {
    fun selectedMember( responseDetail: ResponseMemberList)
}