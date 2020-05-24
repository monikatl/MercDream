package com.baszczyk.mercdream.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room

import androidx.room.RoomDatabase
import com.baszczyk.mercdream.database.enities.Deposit
import com.baszczyk.mercdream.database.enities.Mercedes
import com.baszczyk.mercdream.database.enities.PiggyBank
import com.baszczyk.mercdream.database.enities.User

@Database(entities = [PiggyBank::class, Mercedes::class, Deposit::class, User::class],
version = 11, exportSchema = false)
abstract class PiggyDatabase : RoomDatabase() {

    abstract val piggyDatabaseDao: PiggyDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: PiggyDatabase? = null

        fun getInstance(context: Context): PiggyDatabase {

            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PiggyDatabase::class.java,
                        "piggy_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}