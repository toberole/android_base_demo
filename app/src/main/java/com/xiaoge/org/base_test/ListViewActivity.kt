package com.xiaoge.org.base_test

import android.database.DataSetObserver
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.xiaoge.org.R
import kotlinx.android.synthetic.main.activity_list_view2.*
import java.util.*

class ListViewActivity : AppCompatActivity() {
    private val data = arrayListOf<String>()

    private lateinit var myAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view2)
        initData()
        myAdapter = MyAdapter()
        lv_test.setAdapter(myAdapter)
    }

    private inner class MyAdapter : BaseAdapter() {
        override fun getItem(position: Int): Any {
            return position
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return data.size
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var convertView_x: View?
            var viewHolder: ViewHolder
            if (convertView == null) {
                // TODO 注意View.inflate 与 LayoutInflater.from区别
                // convertView_x = View.inflate(this@ListViewActivity, R.layout.listview_activity_item, null)
                convertView_x = LayoutInflater.from(this@ListViewActivity).inflate(R.layout.listview_activity_item, parent, false)
                viewHolder = ViewHolder()
                viewHolder.tv_test = convertView_x?.findViewById<TextView>(R.id.tv_test)
                convertView_x?.setTag(viewHolder)
            } else {
                convertView_x = convertView
                viewHolder = convertView_x.getTag() as ViewHolder
            }

            viewHolder.tv_test?.setText(data[position])

            return convertView_x!!
        }

        private inner class ViewHolder {
            var tv_test: TextView? = null
        }
    }

    private inner class MyAdapter1 : ListAdapter {
        override fun getItem(position: Int): Any {
            return position
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return data.size
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var convertView_x: View?
            var viewHolder: ViewHolder
            if (convertView == null) {
                convertView_x = LayoutInflater.from(this@ListViewActivity).inflate(R.layout.listview_activity_item, parent, false)
                viewHolder = ViewHolder()
                viewHolder.tv_test = convertView_x?.findViewById<TextView>(R.id.tv_test)
                convertView_x?.setTag(viewHolder)
            } else {
                convertView_x = convertView
                viewHolder = convertView_x.getTag() as ViewHolder
            }

            viewHolder.tv_test?.setText(data[position])

            return convertView_x!!
        }


        override fun registerDataSetObserver(observer: DataSetObserver?) {
        }

        override fun unregisterDataSetObserver(observer: DataSetObserver?) {
        }

        override fun getItemViewType(position: Int): Int = 1
        override fun hasStableIds(): Boolean = true
        override fun areAllItemsEnabled(): Boolean = true
        override fun getViewTypeCount(): Int = 1
        override fun isEnabled(position: Int): Boolean = true
        override fun isEmpty(): Boolean = false

        private inner class ViewHolder {
            var tv_test: TextView? = null
        }
    }

    private fun initData() {
        for (index in 0..100) {
            data.add(index.toString() + " ---")
        }
    }
}
