package com.example.architectcoderspracticauno.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [WizardEntity::class],
    version = 1,
    exportSchema = false
)
abstract class WizardsDatabase: RoomDatabase() {
    abstract fun wizardDao(): WizardsDao
}