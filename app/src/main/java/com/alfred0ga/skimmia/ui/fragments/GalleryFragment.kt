package com.alfred0ga.skimmia.ui.fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alfred0ga.skimmia.R
import kotlinx.android.synthetic.main.fragment_gallery.*

class GalleryFragment : Fragment(R.layout.fragment_gallery) {
    lateinit var rs: Cursor

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestReadStoragePermission()
    }

    private fun requestReadStoragePermission() {
        val readStorage = Manifest.permission.READ_EXTERNAL_STORAGE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context?.let {
                ContextCompat.checkSelfPermission(
                    it.applicationContext,
                    readStorage
                )
            } != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(readStorage), 3)
        } else init()
    }

    private fun init() {
        var cols = listOf<String>(MediaStore.Images.Thumbnails.DATA).toTypedArray()

        rs = context?.contentResolver?.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            cols,null, null, null)!!

        gridView.adapter = ImageAdapter(this.requireContext())
        gridView.setOnItemClickListener { adapterView, view, i, l ->
            rs.moveToPosition(i)
            var path = rs.getString(0)
            val action = GalleryFragmentDirections.actionGalleryFragment2ToGalleryDetailFragment(path)
            findNavController().navigate(action)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            init()
        else {
            Toast.makeText(context, "Permission Required to Fetch Gallery.", Toast.LENGTH_SHORT).show()
        }
    }

    inner class ImageAdapter(context: Context) : BaseAdapter() {
        override fun getCount(): Int {
            return rs.count
        }

        override fun getItem(p0: Int): Any {
            return p0
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            var iv = ImageView(context)
            rs.moveToPosition(p0)
            var path = rs.getString(0)
            var bitmap = BitmapFactory.decodeFile(path)
            iv.setImageBitmap(bitmap)
            iv.layoutParams = AbsListView.LayoutParams(300, 300)
            return iv
        }
    }
}