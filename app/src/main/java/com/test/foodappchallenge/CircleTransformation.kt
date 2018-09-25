package com.test.foodappchallenge

import android.graphics.*
import com.squareup.picasso.Transformation


class CircleTransformation : Transformation {

    override fun key(): String {
        return "CircleTransformation"
    }

    override fun transform(source: Bitmap): Bitmap {
        val bitmapShader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

        val paint = Paint()
        paint.shader = bitmapShader
        paint.isAntiAlias = true

        val minSize = Math.min(source.width, source.height)
        val radius = minSize / 2f

        val bitmap = Bitmap.createBitmap(minSize, minSize, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawCircle(radius, radius, radius, paint)

        source.recycle()
        return bitmap
    }
}