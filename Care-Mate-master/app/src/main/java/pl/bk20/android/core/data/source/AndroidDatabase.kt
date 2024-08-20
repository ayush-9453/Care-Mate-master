package pl.bk20.android.core.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pl.bk20.android.core.data.source.util.Converters
import pl.bk20.android.core.domain.model.Day

@Database(entities = [Day::class], version = 1)
@TypeConverters(Converters::class)
abstract class AndroidDatabase : RoomDatabase() {

    abstract val dayDao: DayDao

    companion object {
        const val DATABASE_NAME = "android_database"
    }
}