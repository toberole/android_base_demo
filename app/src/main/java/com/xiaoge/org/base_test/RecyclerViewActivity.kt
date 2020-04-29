package com.xiaoge.org.base_test

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.xiaoge.org.R
import kotlinx.android.synthetic.main.activity_recycler_view.*

/**
 * RecyclerView比ListView多两级缓存，
 * 支持多个离屏ItemView缓存，支持开发者自定义缓存处理逻辑，
 * 支持所有RecyclerView共用同一个RecyclerViewPool(缓存池)。
 */
class RecyclerViewActivity : AppCompatActivity() {
    private lateinit var adapte: TestAdapter

    private var data = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        initData()
        initView()
    }

    @SuppressLint("WrongConstant")
    private fun initView() {
        // 设置固定大小
        recyclerView.setHasFixedSize(true)
        // 布局manager
        var linearlayoutmanager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearlayoutmanager
        // 垂直方向
        linearlayoutmanager.orientation = OrientationHelper.VERTICAL
        // 适配器
        adapte = TestAdapter(this)
        recyclerView.adapter = adapte
    }

    /**
     * recyclerView优化
     */
    private fun test() {
        recyclerView.setViewCacheExtension(object : RecyclerView.ViewCacheExtension() {
            override fun getViewForPositionAndType(recycler: RecyclerView.Recycler, position: Int, type: Int): View? {
                return null
            }
        })

        // 描述 RecyclerView 是否根据items总宽高重新计算大小。
        // 多数情况 RecyclerView 宽高是 MATCH_PARENT ，设置为true减少重新计算大小次数。
        // 父布局大小改变而导致 RecyclerView 调整不受此参数限制。
        recyclerView.setHasFixedSize(true)

        // RecyclerView离屏缓存 mCachedViews 默认为2，
        // 即视图以移出屏幕后，放到共享缓存池前缓存2个元素，目的优化慢速向前、向后滑动抖动。
        recyclerView.setItemViewCacheSize(4)

        // 多个RecyclerView可以共享一个RecycledViewPool
        var recycledViewPool = RecyclerView.RecycledViewPool()
        recyclerView.setRecycledViewPool(null)
        recyclerView.recycledViewPool.setMaxRecycledViews(0, 0)

        // 指定参数为true避免增删items时引起无关的视图刷新。
        adapte.setHasStableIds(true)
    }

    private fun initData() {
        for (index in 0..100) {
            data.add(index, "data .... $index")
        }
    }

    private inner class TestAdapter(var context: Context) : RecyclerView.Adapter<TestAdapter.ViewHolder>() {
        private lateinit var inflater: LayoutInflater

        init {
            inflater = LayoutInflater.from(context)
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            var v = inflater.inflate(R.layout.recyclerviewactivity_item, parent, false)

            v.setOnClickListener({
                var tag: Int = it.getTag() as Int
                Toast.makeText(this@RecyclerViewActivity, data.get(tag), Toast.LENGTH_SHORT).show()
            })

            var vh = ViewHolder(v)
            return vh
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            var str = data.get(position)
            holder.v?.text = str
            holder.view.setTag(position)
        }

        inner class ViewHolder(var view: View)
            : RecyclerView.ViewHolder(view) {
            var v: TextView? = null

            init {
                v = view.findViewById(R.id.tv_test)
            }
        }
    }
}
