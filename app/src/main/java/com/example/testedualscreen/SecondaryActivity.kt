package com.example.testedualscreen

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2

class SecondaryActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var videoAdapter: VideoAdapter
    private lateinit var selectedImages: Array<Uri>
    private lateinit var selectedVideos: Array<Uri>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secondary)

        viewPager = findViewById(R.id.viewPager)

        selectedImages = intent.getParcelableArrayExtra(EXTRA_IMAGES) as Array<Uri>
        selectedVideos = intent.getParcelableArrayExtra(EXTRA_VIDEOS) as Array<Uri>

        imageAdapter = ImageAdapter(this, selectedImages.toList())
        videoAdapter = VideoAdapter()

        val adapter = ConcatAdapter(imageAdapter, videoAdapter)

        viewPager.adapter = adapter

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position >= imageAdapter.itemCount) {
                    // playing a video
                    videoAdapter.playVideo(position - imageAdapter.itemCount)
                } else {
                    // stopping the video
                    videoAdapter.stopVideo()
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        videoAdapter.releasePlayer()
    }

    companion object {
        const val EXTRA_IMAGES = "images"
        const val EXTRA_VIDEOS = "videos"
    }

}



