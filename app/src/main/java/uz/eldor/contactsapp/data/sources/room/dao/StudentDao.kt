package uz.eldor.contactsapp.data.sources.room.dao

import androidx.room.Dao
import androidx.room.Query
import uz.eldor.contactsapp.data.sources.room.entity.StudentData

@Dao
interface StudentDao : BaseDao<StudentData> {

    @Query("SELECT * FROM StudentData")
    fun getAll(): List<StudentData>

    @Query("DELETE  FROM StudentData WHERE groupId =:id")
    fun deleteByGroupId(id: Long)

    @Query("SELECT * FROM StudentData WHERE groupId=:groupId")
    fun getStudentsByGroupId(groupId: Long): List<StudentData>
}