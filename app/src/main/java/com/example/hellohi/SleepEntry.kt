package com.example.hellohi
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sleep_entries")
data class SleepEntry(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var duration: String,
    var date: String,
    var quality: String
)


