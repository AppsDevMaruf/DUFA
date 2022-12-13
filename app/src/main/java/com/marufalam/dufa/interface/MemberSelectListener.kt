package com.marufalam.dufa.`interface`

import com.marufalam.dufa.data.models.dashboard.ResponseMemberList

interface MemberSelectListener {
    fun selectedMember( responseDetail: ResponseMemberList)
}