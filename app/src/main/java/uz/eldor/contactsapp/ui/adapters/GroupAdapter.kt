package uz.eldor.contactsapp.ui.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.group_item.view.*
import uz.eldor.contactsapp.R
import uz.eldor.contactsapp.data.sources.room.entity.GroupData
import uz.eldor.contactsapp.utils.SingleBlock
import uz.eldor.contactsapp.utils.types.bindItem
import uz.eldor.contactsapp.utils.types.inflate

class GroupAdapter : RecyclerView.Adapter<GroupAdapter.ViewHolder>() {
    private val ls = ArrayList<GroupData>()
    private var listenerItem: SingleBlock<GroupData>? = null
    private var listenerEdit: SingleBlock<GroupData>? = null
    private var listenerDelete: SingleBlock<GroupData>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.group_item))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()
    override fun getItemCount() = ls.size
    fun submitList(data: List<GroupData>) {
        ls.clear()
        ls.addAll(data)
        notifyItemRangeRemoved(0, data.size)
    }

    fun removeItem(data: GroupData) {
        val index = ls.indexOfFirst { it.id == data.id }
        ls.removeAt(index)
        notifyItemRemoved(index)
    }

    fun updateItem(data: GroupData) {
        val index = ls.indexOfFirst { it.id == data.id }
        ls[index] = data
        notifyItemChanged(index)
    }

    fun insertItem(data: GroupData) {
        ls.add(data)
        notifyItemInserted(ls.size - 1)
    }

    fun setOnItemClickListener(block: SingleBlock<GroupData>) {
        listenerItem = block
    }

    fun setOnItemEditListener(block: SingleBlock<GroupData>) {
        listenerEdit = block
    }

    fun setOnItemDeleteListener(block: SingleBlock<GroupData>) {
        listenerDelete = block
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            itemView.apply {
                setOnClickListener { listenerItem?.invoke(ls[adapterPosition]) }
                buttonMore.setOnClickListener { it ->
                    val menu = PopupMenu(context, it)
                    menu.inflate(R.menu.menu_more)
                    menu.setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.menuDelete -> listenerDelete?.invoke(ls[adapterPosition])
                            R.id.menuEdit -> listenerEdit?.invoke(ls[adapterPosition])
                        }
                        true
                    }
                    menu.show()
                }
            }

        }

        fun bind() = bindItem {
            val d = ls[adapterPosition]
            textTitle.text = d.name
        }
    }
}