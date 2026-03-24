package com.example.shoplist.data.room

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShopItemDBModel::class], version = 1, exportSchema = false)
abstract class ShopItemDatabase : RoomDatabase() {

    abstract fun shopItemDao(): ShopItemDao

    companion object {
        private var INSTANCE: ShopItemDatabase? = null
        private var LOCK = Any()
        private var DB_NAME = "DdName"

        fun getInstance(application: Application): ShopItemDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }

                val db = Room.databaseBuilder(
                    application,
                    ShopItemDatabase::class.java,
                    DB_NAME

                ).build()
                INSTANCE = db
                return db
            }
        }
    }


}