package uz.eldor.contactsapp.data.sources.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StudentData(
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0,
    var firstName:String = "",
    var lastName:String="",
    var groupId:Long = 0
)
