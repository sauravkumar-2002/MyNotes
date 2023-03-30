package com.example.mynotes

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.mynotes.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding
   // lateinit var database: database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

       // binding.Title.hint = "rrrr"
       binding.floatingActionButton2.setOnClickListener {
           set_new_data()
       }
    }
    override fun onResume() {
        super.onResume()
        loadData()


    }

    private fun set_new_data() {

        var ed_title=EditText(this)
        var tt=TextView(this)
        tt.text=" My Notes "
        ed_title.hint="Title";
        var ed_content=EditText(this)
        ed_content.hint="Write your content here";
        var ll = LinearLayout(this)
        ll.orientation = LinearLayout.VERTICAL
        ll.addView(ed_title)
        ll.addView(ed_content)

        val dialog = AlertDialog.Builder(this)
        dialog.setCustomTitle(tt)
        dialog.setView(ll)

        dialog.setPositiveButton("Save", DialogInterface.OnClickListener { dialog, which ->
            val c = Calendar.getInstance()

            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)
            val title1=ed_title.text.toString()
            val text=ed_content.text.toString()
            val obj_data=data(0,text,title1,"$day/$month/$year","$hour:$minute  ","day")

            setData_to_room(obj_data)
            dialog.dismiss()
        })
        dialog.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })
        dialog.show()

    }

    private fun setData_to_room(objData: data) {

        GlobalScope.launch {
            var database=database.getDatabase(this@MainActivity)
            database.datadao().insertData(objData)

        }
        Toast.makeText(this,"Saved", Toast.LENGTH_SHORT).show();

    }

    private fun loadData() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recv.layoutManager = layoutManager
        var adapter = Adapter(this)
        binding.recv.adapter = adapter
        var database=database.getDatabase(this@MainActivity)
        database.datadao().getData().observe(this,{
            adapter.setData(it)
        })

    }
}