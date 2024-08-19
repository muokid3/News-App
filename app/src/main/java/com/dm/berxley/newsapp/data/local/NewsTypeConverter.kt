package com.dm.berxley.newsapp.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.dm.berxley.newsapp.domain.models.Source

@ProvidedTypeConverter
class NewsTypeConverter {

    @TypeConverter
    fun sourceToString(source: Source): String {
        return "${source.id},${source.name}"
    }

    @TypeConverter
    fun stringToSource(string: String): Source {
        return string.split(",").let { sourceArray ->
            Source(sourceArray[0], sourceArray[1])
        }
    }
}