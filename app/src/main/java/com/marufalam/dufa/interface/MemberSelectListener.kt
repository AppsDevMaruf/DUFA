package com.marufalam.dufa.`interface`

import com.marufalam.dufa.data.models.search.Data

fun interface MemberSelectListener {
    open fun selectedMember(responseDetail: Data?)
}