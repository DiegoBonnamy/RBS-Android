package com.bonnamy.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class SegmentsMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_segments_menu)

        setTitle("Segments")

        val btnAscensions: ImageButton = findViewById(R.id.btn_ascensions)
        btnAscensions.setOnClickListener {
            val intent = Intent(this, SegmentsListActivity::class.java)
            intent.putExtra(SegmentsListActivity.EXTRA_SEGMENT_TYPE_ID, 1)
            startActivity(intent)
        }

        val btnSprints: ImageButton = findViewById(R.id.btn_sprints)
        btnSprints.setOnClickListener {
            val intent = Intent(this, SegmentsListActivity::class.java)
            intent.putExtra(SegmentsListActivity.EXTRA_SEGMENT_TYPE_ID, 2)
            startActivity(intent)
        }
    }
}