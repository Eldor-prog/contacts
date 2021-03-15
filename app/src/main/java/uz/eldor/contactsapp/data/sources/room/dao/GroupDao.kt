package uz.eldor.contactsapp.data.sources.room.dao

import androidx.room.*
import uz.eldor.contactsapp.data.sources.room.entity.GroupData

@Dao
interface GroupDao : BaseDao<GroupData> {

    @Query("SELECT * FROM GroupData")
    fun getAll(): List<GroupData>

}