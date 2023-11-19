package com.example.hellohi

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SleepDao {
    @Insert
    fun insert(sleepEntry: SleepEntry): Long

    @Query("SELECT * FROM sleep_entries")
    fun getAllEntries(): Flow<List<SleepEntry>>  // Corrected function name with capital "A"

    @Query("SELECT AVG(CAST(duration AS REAL)) FROM sleep_entries")
    fun getAvgDuration(): Double

}
