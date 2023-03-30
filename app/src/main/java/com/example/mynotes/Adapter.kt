package com.example.mynotes

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class Adapter(var context: Context) : RecyclerView.Adapter<Adapter.ViewHolder>() {
     var data_list= mutableListOf<data>()
    fun setData(prob_list: List<data>){
        this.data_list= prob_list as MutableList<data>
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title_list);
        val date: TextView = itemView.findViewById(R.id.date_list);
        val time: TextView = itemView.findViewById(R.id.time_list);
        val text: TextView = itemView.findViewById(R.id.text_list);
        val edit:ImageView=itemView.findViewById(R.id.edit_list);
        val delete:ImageView=itemView.findViewById(R.id.delete_list);
        //val index: TextView = itemView.findViewById(R.id.index);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.i("check ","hhncwholder")
        val inflater = LayoutInflater.from(parent.context);
        val view = inflater.inflate(R.layout.list_item, parent, false);
        return (ViewHolder(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.date.text=data_list.get(position).date.toString()
        holder.time.text=data_list.get(position).time.toString()
        holder.title.text=data_list.get(position).title.toString()
        holder.text.text=data_list.get(position).text.toString()
        holder.edit.setOnClickListener {

          set_edit_note( data_list.get(position).id.toLong(),position)
        }
        holder.delete.setOnClickListener {
            set_delete_note( data_list.get(position).id)
        }
    }

    private fun set_edit_note(id:Long,position: Int) {
        var ed_title= EditText(context)
        var tt=TextView(context)
        tt.text=" Edit Here "
        ed_title.setText(data_list.get(position).title);
        var ed_content= EditText(context)
        ed_content.setText(data_list.get(position).text)
        var ll = LinearLayout(context)
        ll.orientation = LinearLayout.VERTICAL
        ll.paddingTop
        ll.addView(ed_title)
        ll.addView(ed_content)

        val dialog = AlertDialog.Builder(context)
        dialog.setCustomTitle(tt)
        dialog.setView(ll)

        dialog.setPositiveButton("Save", DialogInterface.OnClickListener { dialog, which ->
            GlobalScope.launch {
                val c = Calendar.getInstance()

                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)

                val hour = c.get(Calendar.HOUR_OF_DAY)
                val minute = c.get(Calendar.MINUTE)
                val title1=ed_title.text.toString()
                val text=ed_content.text.toString()
                val obj_data=data(id,text,title1,"$day/$month/$year","$hour:$minute  ","day")
                GlobalScope.launch {
                    var database=database.getDatabase(context)
                    database.datadao().update_data(id,title1,text,"$day/$month/$year","$hour:$minute ")

                }
            }
            Toast.makeText(context,"Updated",Toast.LENGTH_SHORT).show();
            dialog.dismiss()
        })
        dialog.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })
        dialog.show()
    }

    private fun set_delete_note(id:Long) {
        var tt=TextView(context)
        tt.text=" Are You Sure to delete This Note "
        val dialog = AlertDialog.Builder(context)
        dialog.setCustomTitle(tt)

        dialog.setPositiveButton("Delete", DialogInterface.OnClickListener { dialog, which ->
            GlobalScope.launch {
                var database=database.getDatabase(context)
                database.datadao().delete_data(id)
                //Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show();

            }
            Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show();
            dialog.dismiss()
        })
        dialog.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })
        dialog.show()
    }

    override fun getItemCount(): Int {

        return data_list.size;
    }
}