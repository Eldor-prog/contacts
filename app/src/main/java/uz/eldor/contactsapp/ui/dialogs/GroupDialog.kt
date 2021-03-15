package uz.eldor.contactsapp.ui.dialogs

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.dialog_group.view.*
import uz.eldor.contactsapp.R
import uz.eldor.contactsapp.data.sources.room.entity.GroupData
import uz.eldor.contactsapp.utils.SingleBlock

class GroupDialog(context: Context, actionName: String) : AlertDialog(context) {
    private val contentView =
        LayoutInflater.from(context).inflate(R.layout.dialog_group, null, false)
    private var groupData: GroupData? = null
    private var listener:SingleBlock<GroupData> ?=null

    init {
        setView(contentView)
        setButton(BUTTON_POSITIVE, actionName) { _, _ ->
            val data = groupData ?: GroupData()
            data.name = contentView.inputName.text.toString()
            listener?.invoke(data)
        }
        setButton(BUTTON_NEGATIVE, "Cancel"){_,_ ->}
    }
    fun setGroupData(groupData: GroupData) = with(contentView){
        this@GroupDialog.groupData = groupData
        inputName.setText(groupData.name)
    }
    fun setOnClickListener(block: SingleBlock<GroupData>){
        listener = block
    }
}