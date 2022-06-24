package com.fonrouge.rbpiHmi.db

import com.fonrouge.rbpiHmi.data.PLCConfig

interface DAOPLCConfig {
    suspend fun allConfigs(): List<PLCConfig>
    suspend fun config(id: Int): PLCConfig?
    suspend fun addNewConfig(title: String, body: String): PLCConfig?
    suspend fun editConfig(id: Int, title: String, body: String): Boolean
    suspend fun deleteConfig(id: Int): Boolean
}
