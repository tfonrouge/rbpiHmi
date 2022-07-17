package com.fonrouge.rbpiHmi.dataComm

import com.fonrouge.rbpiHmi.data.QueryAction
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@kotlinx.serialization.Serializable
class ResetQuery(
    @EncodeDefault
    override val commId: Long = IQuery.commId,
    @EncodeDefault
    override val action: QueryAction = QueryAction.Reset
) : IQuery
