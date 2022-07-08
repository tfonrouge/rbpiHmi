package com.fonrouge.rbpiHmi.dataComm

import com.fonrouge.rbpiHmi.data.QueryAction
import com.fonrouge.rbpiHmi.data.SensorsConfig
import kotlinx.serialization.EncodeDefault

@kotlinx.serialization.Serializable
class HelloQuery(
    val sensorsConfig: SensorsConfig
) : IQuery {
    @EncodeDefault
    override val commId: Long = IQuery.commId
    @EncodeDefault
    override val action: QueryAction = QueryAction.Hello
}
