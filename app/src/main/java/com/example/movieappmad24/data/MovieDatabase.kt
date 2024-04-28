package com.example.movieappmad24.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieImage
import com.example.movieappmad24.workers.WorkManagerDatabaseRepository

@Database(
    entities = [Movie::class, MovieImage::class],
    version = 2,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDataAccessObject(): MovieDataAccessObject

    companion object {
        @Volatile
        private var Instance: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, MovieDatabase::class.java, "movie_db")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback = seedDatabase(context = context))
                    .build()
                    .also {
                        Instance = it
                    }
            }
        }

        private fun seedDatabase(context: Context): Callback { // https://stackoverflow.com/questions/59238447/android-kotlin-room-with-corountines-how-to-create-db-preseed-data-and-popu
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    val workManagerDatabase = WorkManagerDatabaseRepository(context = context)

                    workManagerDatabase.seedDatabaseWorkRequest()
                }
            }
        }
    }
}