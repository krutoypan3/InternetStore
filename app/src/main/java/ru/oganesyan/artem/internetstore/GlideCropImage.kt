package ru.oganesyan.artem.internetstore

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayoutStates
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

class GlideCropImage(
    private val imageView: ImageView,
    private val cropMultiply: Float
) : BitmapTransformation() {
    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap =
        Bitmap.createBitmap(
            toTransform,
            0,
            0,
            (imageView.width * cropMultiply).toInt(),
            (imageView.height * cropMultiply).toInt()   // numer of pixels
        )

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {}
}