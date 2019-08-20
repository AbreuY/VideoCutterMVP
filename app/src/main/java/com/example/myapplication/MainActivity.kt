package com.example.myapplication

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        VideoFileUtils.clearAllMediaCache(this)
               val input = resources.openRawResource(R.raw.testtest)
        val b = ByteArray(input.available())
        input.read(b)

        val file = File(VideoFileUtils.getMediaCacheDirPath(this) + "/" + "temp.mp4")

        file.writeBytes(b)

        button.apply {
            setOnClickListener {

                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Log.v("xxxx","Permission is granted")

                    Cutter(file,context)
                }else{
                    ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 111)
                }


            }
        }

    }
}
