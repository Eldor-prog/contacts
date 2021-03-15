package uz.eldor.contactsapp.ui.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_student.*
import uz.eldor.contactsapp.R
import uz.eldor.contactsapp.data.sources.room.AppDatabase
import uz.eldor.contactsapp.ui.adapters.StudentAdapter
import uz.eldor.contactsapp.ui.dialogs.StudentDialog

class StudentActivity : AppCompatActivity() {
    private val adapter = StudentAdapter()
    private val db by lazy { AppDatabase.getDatabase(this) }
    private val studentDao by lazy { db.studentDao() }
    private val groupId by lazy { intent.extras?.getLong("GROUP_ID") ?: 0 }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)
        title = "Students"
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = adapter
        adapter.submitList(studentDao.getStudentsByGroupId(groupId))
        adapter.setOnItemDeleteListener {
            studentDao.delete(it)
            adapter.removeItem(it)
        }
        adapter.setOnItemEditListener {
            val dialog = StudentDialog(this, "Edit")
            dialog.setStudentData(it)
            dialog.setOnClickListener {
                studentDao.update(it)
                adapter.updateItem(it)
            }
            dialog.show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuAdd -> {
                val dialog = StudentDialog(this, "Add")

                dialog.setOnClickListener {
                    it.groupId = groupId
                    val id = studentDao.insert(it)
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