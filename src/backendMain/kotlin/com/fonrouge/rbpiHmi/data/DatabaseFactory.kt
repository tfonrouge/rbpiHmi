package com.fonrouge.rbpiHmi.data

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.nio.file.Files
import kotlin.io.path.Path

const val database = "db"

object DatabaseFactory {
    fun init() {
        if (!Files.isDirectory(Path(database))) {
            Files.createDirectory(Path(database))
        }
        val database = Database.connect("jdbc:sqlite:$database/rpbHmi", "org.sqlite.JDBC")
        transaction(database) {
            SchemaUtils.create(PLCConfigs)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}
