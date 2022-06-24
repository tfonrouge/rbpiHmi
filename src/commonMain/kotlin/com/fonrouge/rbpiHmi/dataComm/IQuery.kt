package com.fonrouge.rbpiHmi.dataComm

import com.fonrouge.rbpiHmi.data.QueryAction

interface IQuery {
    val commId: Long
    val action: QueryAction

    companion object {
        var commId = 0L
            get() {
                return field++
            }
    }
}
