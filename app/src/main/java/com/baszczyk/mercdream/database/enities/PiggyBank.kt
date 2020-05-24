package com.baszczyk.mercdream.database.enities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "piggy")
data class PiggyBank(
    @PrimaryKey(autoGenerate = true)
    var piggyId: Long = 0L,

    @ColumnInfo(name = "piggy_name")
    val piggyName: String,

    @ColumnInfo(name = "user_id")
    val userId: Long,

    @ColumnInfo(name="mercedes_id")
    val mercedesId: Long,

    @ColumnInfo(name="actual_amount")
    var actualAmount: Double

)