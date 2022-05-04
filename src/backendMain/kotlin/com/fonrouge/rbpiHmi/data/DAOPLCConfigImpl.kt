package com.fonrouge.rbpiHmi.data

class DAOPLCConfigImpl : DAOPLCConfig {
    override suspend fun allConfigs(): List<PLCConfig> {
        TODO("Not yet implemented")
    }

    override suspend fun config(id: Int): PLCConfig? {
        TODO("Not yet implemented")
    }

    override suspend fun addNewConfig(title: String, body: String): PLCConfig? {
        TODO("Not yet implemented")
    }

    override suspend fun editConfig(id: Int, title: String, body: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteConfig(id: Int): Boolean {
        TODO("Not yet implemented")
    }
}
