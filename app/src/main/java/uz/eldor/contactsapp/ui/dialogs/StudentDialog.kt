package uz.eldor.contactsapp.ui.dialogs

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.dialog_student.view.*
import uz.eldor.contactsapp.R
import uz.eldor.contactsapp.data.sources.room.entity.StudentData
import uz.eldor.contactsapp.utils.SingleBlock

class StudentDialog(context: Context, actionName: String) : AlertDialog(context) {
    private val contentView =
        LayoutInflater.from(context).inflate(R.layout.dialog_student, null, false)
    private var listener: SingleBlock<StudentData>? = null
    private var studentData: StudentData? = null

    init {
        setView(contentView)
        setButton(BUTTON_POSITIVE, actionName){_,_->
            val data = studentData?: StudentData()
            data.firstName = contentView.inputFirstName.text.toString()
            data.lastName = contentView.inputLastName.text.toString()
            listener?.invoke(data)
        }
        setButton(BUTTON_NEGATIVE, "Cancel"){_,_ ->}
    }
    fun setStudentData(studentData: StudentData) = with(contentView){
        this@StudentDialog.studentData = studentData
        inputFirstName.setText(studentData.firstName)
        inputLastName.setText(studentData.lastName)
    }
    fun setOnClickListener(block: SingleBlock<StudentData>){
        listener = block
    }
}