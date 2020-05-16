package com.baszczyk.mercdream.database


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mercedes")
data class Mercedes (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "mercedes_id")
    var mercedesId: Long = 0L,

    @ColumnInfo(name="name")
    val name: String = "Mercedes",

    @ColumnInfo(name="surname")
    val surname: String,

    @ColumnInfo(name="price")
    val price: Double,

    @ColumnInfo(name = "version")
    val version: String,

    @ColumnInfo(name="engine_capacity")
    val engineCapacity: String,

    @ColumnInfo(name = "power_engine")
    val powerEngine: String
)

@Entity(tableName = "piggy")
data class PiggyBank(
    @PrimaryKey(autoGenerate = true)
    var piggyId: Long = 0L,

    @ColumnInfo(name = "user_id")
    val userId: Long,

    @ColumnInfo(name="mercedes_id")
    val mercedesId: Long,

    @ColumnInfo(name="actual_amount")
    var actualAmount: Double

)

@Entity(tableName = "deposit")
data class Deposit (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "deposit_id")
    var depositId: Long = 0L,

    @ColumnInfo(name = "amount")
    val amount: Double,

    @ColumnInfo(name = "date")
    val date: String,

    @ColumnInfo(name="mercedes_id")
    val mercedesId: Long)

@Entity(tableName = "user")
data class User (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    var userId: Long = 0L,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "password")
    val password: String,

    @ColumnInfo(name = "email")
    val email: String
)










