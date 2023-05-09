package com.example.testedualscreen

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.testedualscreen.VideoAdapter.VideoViewHolder as VideoViewHolder1


class MainActivity : AppCompatActivity() {

    private lateinit var selectedImages: List<Uri>
    private lateinit var selectedVideos: List<Uri>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun selectImages() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(intent, REQUEST_SELECT_IMAGES)
    }

    fun selectVideos() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "video/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(intent, REQUEST_SELECT_VIDEOS)
    }

    fun play() {
        if (::selectedImages.isInitialized && ::selectedVideos.isInitialized) {
            val intent = Intent(this, SecondaryActivity::class.java)
            intent.putExtra(SecondaryActivity.EXTRA_IMAGES, selectedImages.toTypedArray())
            intent.putExtra(SecondaryActivity.EXTRA_VIDEOS, selectedVideos.toTypedArray())
            startActivity(intent)
        } else {
            Toast.makeText(this, "Please select images and videos first", Toast.LENGTH_SHORT).show()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_SELECT_IMAGES -> {
                    selectedImages = getSelectedUris(data)
                    Toast.makeText(this, "${selectedImages.size} images selected", Toast.LENGTH_SHORT).show()
                }
                REQUEST_SELECT_VIDEOS -> {
                    selectedVideos = getSelectedUris(data)
                    Toast.makeText(this, "${selectedVideos.size} videos selected", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getSelectedUris(data: Intent?): List<Uri> {
        val uris = mutableListOf<Uri>()
        val clipData = data?.clipData
        if (clipData != null) {
            for (i in 0 until clipData.itemCount) {
                uris.add(clipData.getItemAt(i).uri)
            }
        } else {
            val uri = data?.data
            if (uri != null) {
                uris.add(uri)
            }
        }
        return uris
    }

    companion object {
        private const val REQUEST_SELECT_IMAGES = 1
        private const val REQUEST_SELECT_VIDEOS = 2
    }

}
class ImageAdapter(private val context: Context, private val images: List<Uri>) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return images.size
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind() {
            setImageURI()
        }
    }

}

private fun setImageURI() {

}

class VideoAdapter() : RecyclerView.Adapter<VideoViewHolder1>(), Parcelable {
    interface VideoViewHolder

    constructor(parcel: Parcel) : this() {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return TODO("Provide the return value")
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the [ViewHolder.itemView] to reflect the item at the given
     * position.
     *
     *
     * Note that unlike [android.widget.ListView], RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the `position` parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use [ViewHolder.getAdapterPosition] which will
     * have the updated adapter position.
     *
     * Override [.onBindViewHolder] instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     * item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VideoAdapter> {
        override fun createFromParcel(parcel: Parcel): VideoAdapter {
            return VideoAdapter(parcel)
        }

        override fun newArray(size: Int): Array<VideoAdapter?> {
            return arrayOfNulls(size)
        }
    }
}