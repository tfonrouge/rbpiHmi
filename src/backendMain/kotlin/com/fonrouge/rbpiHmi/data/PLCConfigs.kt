package com.fonrouge.rbpiHmi.data

import org.jetbrains.exposed.dao.id.IntIdTable

object PLCConfigs : IntIdTable() {
    val dateCreated = long("dateCreated")
    val dateModified = long("dateModified")
    val title = varchar("title", 128)
    val body = varchar("body", 1024)
}
