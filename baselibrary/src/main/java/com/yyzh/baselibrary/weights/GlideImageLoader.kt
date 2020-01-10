package com.yyzh.baselibrary.weights

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.youth.banner.loader.ImageLoader

class GlideImageLoader: ImageLoader() {
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        //Glide 加载图片简单用法
        Glide.with(context).load(path).into(imageView)
        //用fresco加载图片简单用法，记得要写下面的createImageView方法
        var uri=Uri.parse(path as String)
        imageView?.setImageURI(uri)
    }
}