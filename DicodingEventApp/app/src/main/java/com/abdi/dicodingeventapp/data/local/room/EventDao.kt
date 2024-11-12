package com.abdi.dicodingeventapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.abdi.dicodingeventapp.data.local.entity.EventEntity

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEvents(events: EventEntity)

    @Query("SELECT * FROM event WHERE id = :eventId LIMIT 1")
    fun getEventById(eventId: Int): EventEntity?

    @Update
    fun updateEvent(event: EventEntity)

    @Query("DELETE FROM event WHERE id = :eventId")
    fun deleteEvent(eventId: Int)

    @Query("SELECT COUNT(*) > 0 FROM event WHERE id = :eventId AND favorite = 1")
    fun isEventFavorite(eventId: Int): LiveData<Boolean>

    @Query("SELECT * FROM event WHERE favorite = 1")
    fun getFavoriteEvents(): LiveData<List<EventEntity>>

    @Query("SELECT * FROM event WHERE name LIKE :name")
    fun searchEventByName(name: String): List<EventEntity>

    @Query("SELECT * FROM event WHERE name LIKE :name AND favorite = 1")
    fun searchFavoriteEventByName(name: String): List<EventEntity>

}


