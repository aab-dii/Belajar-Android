package com.abdi.dicodingeventapp.data.local.room

import android.content.Context
import androidx.room.*
import com.abdi.dicodingeventapp.data.local.entity.EventEntity

@Database(entities = [EventEntity::class], version = 2)
abstract class EventDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao

    companion object {
        @Volatile
        private var INSTANCE: EventDatabase? = null
        fun getInstance(context: Context): EventDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    EventDatabase::class.java, "Event.db"
                ).build().also { INSTANCE = it }
            }
    }
}
