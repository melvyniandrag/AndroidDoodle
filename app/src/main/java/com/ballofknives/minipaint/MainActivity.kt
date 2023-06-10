package com.ballofknives.minipaint

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val myCanvasView = MyCanvasView(this)
        //myCanvasView.contentDescription = getString(R.string.canvasContentDescription)
        setContentView(R.layout.activity_main)

        var button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            saveImage()
        }
    }

    private fun saveImage()  {
        val baos = ByteArrayOutputStream()
        var myCanvasView : MyCanvasView = findViewById(R.id.myCanvasView)
        myCanvasView.extraBitmap.compress(Bitmap.CompressFormat.PNG, 0, baos) // It can be also saved it as JPEG
        val bitmapdata = baos.toByteArray()

        val fileOutputStream: FileOutputStream
        try{
            fileOutputStream = openFileOutput("share.png", Context.MODE_PRIVATE)
            fileOutputStream.write(bitmapdata)
        } catch (e : Exception ){
            e.printStackTrace()
        }
        try {
            val imageFile = File(filesDir, "share.png")
            val uri = FileProvider.getUriForFile(this, packageName, imageFile)
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.type = "image/png"
            startActivity(intent)
        }
        catch(e:Exception){
            e.printStackTrace()
        }

    }


}