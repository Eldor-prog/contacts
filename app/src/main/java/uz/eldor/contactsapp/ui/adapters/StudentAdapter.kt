package uz.eldor.contactsapp.ui.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.student_item.view.*
import uz.eldor.contactsapp.R
import uz.eldor.contactsapp.data.sources.room.entity.StudentData
import uz.eldor.contactsapp.utils.SingleBlock
import uz.eldor.contactsapp.utils.types.bindItem
import uz.eldor.contactsapp.utils.types.inflate

class StudentAdapter : RecyclerView.Adapter<StudentAdapter.ViewHolder>() {
    private val ls = ArrayList<StudentData>()
    private var listenerItem: SingleBlock<StudentData>? = null
    private var listenerEdit: SingleBlock<StudentData>? = null
    private var listenerDelete: SingleBlock<StudentData>? = null

    fun submitList(data: List<StudentData>) {
        ls.clear()
        ls.addAll(data)
        notifyItemRangeRemoved(0, data.size)
    }

    fun removeItem(data: StudentData) {
        val index = ls.indexOfFirst { it.id == data.id }
        ls.removeAt(index)
        notifyItemRemoved(index)
    }

    fun insertItem(data: StudentData) {
        val index = ls.indexOfFirst { it.id == data.id }
        ls.add(data)
        notifyItemInserted(ls.size - 1)
    }

    fun updateItem(data: StudentData) {
        val index = ls.indexOfFirst { it.id == data.id}
        ls[index] = data
        notifyItemChanged(index)
    }
    fun setOnItemClickListener(block: SingleBlock<StudentData>){
        listenerItem = block
    }
    fun setOnItemEditListener(block: SingleBlock<StudentData>){
        listenerEdit = block
    }
    fun setOnItemDeleteListener(block: SingleBlock<StudentData>){
        listenerDelete = block
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.apply {
                setOnClickListener { listenerItem?.invoke(ls[adapterPosition]) }
                buttonMore.setOnClickListener {
                    val menu = PopupMenu(context, it)
                    menu.inflate(R.menu.menu_more)
                    menu.setOnMenuItemClickListener {
                        when(it.itemId){
                            R.id.menuDelete->listenerDelete?.invoke(ls[adapterPosition])
                            R.id.menuEdit->listenerEdit?.invoke(ls[adapterPosition])
                        }
                        true
                    }
                    menu.show()
                }
            }
        }

        fun bind() = bindItem {
            val d = ls[adapterPosition]
            textTitle.text = d.firstName
            textInfo.text = d.lastName

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.student_item))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()

    override fun getItemCount() = ls.size
}