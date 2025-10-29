package com.example.redblue

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private val data = mutableListOf<Int>()
    private lateinit var adapter: RectAdapter

    private companion object { const val KEY_COUNT = "count" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val restored = savedInstanceState?.getInt(KEY_COUNT) ?: 0
        if (data.isEmpty() && restored > 0) repeat(restored) { data.add(it) }

        val span = if (resources.configuration.orientation ==
            Configuration.ORIENTATION_PORTRAIT) 3 else 4

        val rv = findViewById<RecyclerView>(R.id.recyclerView)
        val lm = GridLayoutManager(this, span)
        rv.layoutManager = lm
        adapter = RectAdapter(data)
        rv.adapter = adapter

        val fab = findViewById<FloatingActionButton>(R.id.addButton)
        fab.setOnClickListener {
            data.add(data.size)
            adapter.notifyItemInserted(data.lastIndex)

            val scroller = object : LinearSmoothScroller(this) {
                override fun calculateSpeedPerPixel(dm: android.util.DisplayMetrics): Float {
                    return 150f / dm.densityDpi
                }
            }
            scroller.targetPosition = data.lastIndex
            lm.startSmoothScroll(scroller)
        }

        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visible = lm.findLastCompletelyVisibleItemPosition()
                if (data.isNotEmpty() && visible == data.lastIndex) fab.show() else fab.hide()
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(KEY_COUNT, data.size)
        super.onSaveInstanceState(outState)
    }
}
