package com.alfred0ga.skimmia.ui.fragments

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.alfred0ga.skimmia.R
import kotlinx.android.synthetic.main.fragment_gallery_detail.*

class GalleryDetailFragment : Fragment(R.layout.fragment_gallery_detail) {
    private val args by navArgs<GalleryDetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var bitmap = BitmapFactory.decodeFile(args.path)
        ivImg.setImageBitmap(bitmap)
    }

}