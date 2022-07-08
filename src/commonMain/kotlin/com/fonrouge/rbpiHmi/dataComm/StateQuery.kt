package com.fonrouge.rbpiHmi.dataComm

import com.fonrouge.rbpiHmi.data.QueryAction
import kotlinx.serialization.EncodeDefault

@kotlinx.serialization.Serializable
class StateQuery(
) : IQuery {
    @EncodeDefault
    override val commId: Long = IQuery.commId
    @EncodeDefault
    override val action: QueryAction = QueryAction.State
}
