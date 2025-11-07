package com.example.redblue

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private val data = mutableListOf<Int>()
    private lateinit var adapter: RectAdapter

    private companion object {
        const val KEY_COUNT = "count"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // восстановление количества карточек при повороте экрана
        val restored = savedInstanceState?.getInt(KEY_COUNT) ?: 0
        if (data.isEmpty() && restored > 0) repeat(restored) { data.add(it) }

        // выбор количества колонок в зависимости от ориентации
        val span = if (resources.configuration.orientation ==
            Configuration.ORIENTATION_PORTRAIT
        )
            resources.getInteger(R.integer.span_portrait)
        else
            resources.getInteger(R.integer.span_landscape)

        // настройка RecyclerView и адаптера
        val rv = findViewById<RecyclerView>(R.id.recyclerView)
        val lm = GridLayoutManager(this, span)
        rv.layoutManager = lm
        adapter = RectAdapter(data)
        rv.adapter = adapter

        // кнопка добавления нового квадрата
        val fab = findViewById<FloatingActionButton>(R.id.addButton)
        fab.setOnClickListener {
            data.add(data.size)


            Toast.makeText(this, "add ${data.lastIndex + 1}", Toast.LENGTH_SHORT).show()

            adapter.notifyItemInserted(data.lastIndex)

            // плавно прокручиваем к последнему элементу
            val scroller = object : LinearSmoothScroller(this) {
                override fun calculateSpeedPerPixel(dm: android.util.DisplayMetrics): Float {
                    // чем больше число, тем медленнее прокрутка, ага, не наоборот XD
                    return 150f / dm.densityDpi
                }
            }
            scroller.targetPosition = data.lastIndex
            lm.startSmoothScroll(scroller)
        }

        // показываем кнопку только внизу
        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visible = lm.findLastCompletelyVisibleItemPosition()
                if (data.isNotEmpty() && visible == data.lastIndex) fab.show() else fab.hide()
            }
        })
    }

    // сохраняем количество элементов при перевороте экрана
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(KEY_COUNT, data.size)
        super.onSaveInstanceState(outState)
    }
}
