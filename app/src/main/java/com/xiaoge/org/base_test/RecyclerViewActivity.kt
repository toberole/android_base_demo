package com.xiaoge.org.base_test

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
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

        // 设置装饰
        recyclerView.addItemDecoration(MyItemDecoration(OrientationHelper.HORIZONTAL))
//        recyclerView.addItemDecoration(LinearDividerDecoration(OrientationHelper.VERTICAL))

        // 布局manager
        var linearlayoutmanager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearlayoutmanager
        // 垂直方向
        linearlayoutmanager.orientation = OrientationHelper.VERTICAL


        // 适配器
        adapte = TestAdapter(this)
        recyclerView.adapter = adapte
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

    /**
     * item 装饰分割线
     */
    private inner class MyItemDecoration(var orientation: Int)
        : RecyclerView.ItemDecoration() {
        private var line_size = 50
        private lateinit var divider: Drawable
        private lateinit var paint: Paint

        init {
            paint = Paint(Paint.ANTI_ALIAS_FLAG)
            paint.color = Color.RED

            divider = getDrawable(R.drawable.divider)
        }

        /**
         * 设置条目周边的偏移量
         * 类似设置view#margin
         */
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.bottom = line_size
//            if (orientation == RecyclerView.HORIZONTAL) {
//                outRect.bottom = line_size
//            } else if (orientation == RecyclerView.VERTICAL) {
//                outRect.right = line_size
//            }
        }

        /**
         * getItemOffsets()是相对每个item而言的，
         * 即每个item都会偏移出相同的装饰区域。
         * 而onDraw()则不同，它是相对Canvas来说的，
         * 通俗的说就是要自己找到要画的线的位置，
         * 这是自定义ItemDecoration中唯一比较难的地方了。
         */
        override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            super.onDraw(c, parent, state)
//            if (orientation == RecyclerView.HORIZONTAL) {
//                drawVertical(c, parent, state);
//            } else if (orientation == RecyclerView.VERTICAL) {
//                drawHorizontal(c, parent, state);
//            }
            // drawVertical(c, parent, state);
            drawHorizontal(c, parent, state);
        }

        private fun drawHorizontal(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            val childSize = parent.childCount
            for (i in 0 until childSize) {
                val child = parent.getChildAt(i)
                val layoutParams = child.layoutParams as RecyclerView.LayoutParams

                val left = parent.paddingLeft
                val top = child.bottom + layoutParams.bottomMargin
                val right = parent.width- parent.paddingRight
                val bottom = top + line_size

                canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
            }
        }

        /**
         * 画竖直分割线
         */
        private fun drawVertical(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            val childCount = parent.childCount
            for (i in 0 until childCount) {
                val child = parent.getChildAt(i)
                val params = child.layoutParams as RecyclerView.LayoutParams

                val left = child.right + params.rightMargin
                val top = child.top - params.topMargin
                val right = left + line_size
                val bottom = child.bottom + params.bottomMargin

                canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
            }
        }
    }

    class LinearDividerDecoration(@param:RecyclerView.Orientation private val mOrientation: Int) : RecyclerView.ItemDecoration() {
        private val mDividerHeight = 40
        private val mDividerColor = -0x10000
        private val mPaint: Paint

        init {
            mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            mPaint.color = mDividerColor
        }

        override fun getItemOffsets(@NonNull outRect: Rect, @NonNull view: View, @NonNull parent: RecyclerView, @NonNull state: RecyclerView.State) {
            if (mOrientation == LinearLayoutManager.VERTICAL) {
                outRect[0, 0, 0] = mDividerHeight
            } else {
                outRect[0, 0, mDividerHeight] = 0
            }
        }

        override fun onDraw(@NonNull canvas: Canvas, @NonNull parent: RecyclerView, @NonNull state: RecyclerView.State) {
            canvas.save()
            val childCount = parent.childCount
            for (i in 0 until childCount) {
                val childAt = parent.getChildAt(i)
                val left = 0
                val top = childAt.bottom
                val right = parent.width
                val bottom = childAt.bottom + mDividerHeight
                canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
            }
            canvas.restore()
        }
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
}
