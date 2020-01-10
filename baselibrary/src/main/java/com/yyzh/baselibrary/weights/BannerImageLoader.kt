package com.yyzh.baselibrary.weights

import android.content.Context
import android.widget.ImageView
import com.youth.banner.loader.ImageLoader
import com.yyzh.baselibrary.utlis.GlideUtils

/*
    Banner图片加载器
 */
class BannerImageLoader : ImageLoader() {
    override fun displayImage(context: Context, path: Any, imageView: ImageView) {
        GlideUtils.loadUrlImage(context, path.toString(), imageView)
    }
}
