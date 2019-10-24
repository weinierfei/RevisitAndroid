package com.dragon.revisitandroid.view

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.dragon.revisitandroid.R

/**
 * Description:
 *
 * @author guoyongping
 * @date   2019-10-20 10:35
 */
class TestHorizontalViewActivity : AppCompatActivity() {

    private lateinit var lvOne: ListView
    private lateinit var lvTwo: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_view)
        lvOne = findViewById(R.id.lv_one)
        lvTwo = findViewById(R.id.lv_two)

        val sts1 = arrayOf(
            "1",
            "2",
            "3",
            "4",
            "5"
        )
        val adapter1 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sts1)
        lvOne.adapter = adapter1

        val sts2 = arrayOf(
            "A",
            "B",
            "C",
            "D",
            "E"
        )
        val adapter2 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sts2)
        lvTwo.adapter = adapter2


    }
}