package uz.eldor.contactsapp.ui.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_group.*
import uz.eldor.contactsapp.R
import uz.eldor.contactsapp.data.sources.room.AppDatabase
import uz.eldor.contactsapp.ui.adapters.GroupAdapter
import uz.eldor.contactsapp.ui.dialogs.GroupDialog

class GroupActivity : AppCompatActivity() {
    private val adapter = GroupAdapter()
    private val db by lazy { AppDatabase.getDatabase(this) }
    private val groupDao by lazy { db.groupDao() }
    private val studentDao by lazy { db.studentDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)
        title = "Groups"
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = adapter
        adapter.submitList(groupDao.getAll())
        adapter.setOnItemClickListener {
            startActivity(Intent(this, StudentActivity::class.java).apply {
                putExtra(
                    "GROUP_ID",
                    it.id
                )
            })
        }
        adapter.setOnItemEditListener {
            val dialog = GroupDialog(this, "Edit")
            dialog.setGroupData(it)
            dialog.setOnClickListener {
                groupDao.update(it)
                adapter.updateItem(it)
            }
            dialog.show()
        }
        adapter.setOnItemDeleteListener {
            groupDao.delete(it)
            studentDao.deleteByGroupId(it.id)
            adapter.removeItem(it)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuAdd -> {
                val dialog = GroupDialog(this, "Add")
                dialog.setOnClickListener {
                    val id = groupDao.insert(it)
                    it.id = id
                    adapter.insertItem(it)
                    list.smoothScrollToPosition(adapter.itemCount - 1)
                }
                dialog.show()
            }
        }
        return true
    }
}