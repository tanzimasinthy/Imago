package com.example.space

import android.app.AlertDialog
import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.AsyncTask
import android.os.Environment
import android.view.LayoutInflater
import android.widget.ProgressBar
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class AsyncDownloadImage(
    val callback: CallBack,
    val context: Context,
    var status: String
):AsyncTask<String, Int,Int>()
{
    var mAlert:AlertDialog? = null

    override fun onPreExecute() {
        val mDialogView = LayoutInflater.from(context).inflate(R.layout.layout_progress, null)
        val mBuilder = android.app.AlertDialog.Builder(context).setView(mDialogView)
        //Glide.with(p!!).load(data.urls.regular).centerCrop().into(mDialogView.dialog_img)
        mAlert = mBuilder.show()
        super.onPreExecute()
    }

    override fun doInBackground(vararg params: String?): Int {

        var t = params.toString()
        t = t.replace("/", "")
        t = t.replace("http", "")
        t = t.replace(":", "")
        t = t.replace("download", "_imago")
        t = t.replace(".", "")


            Glide.with(context)
                .asBitmap()
                .load(params)
                .fitCenter()
                .into(object : SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        //Toast.makeText(this@ViewImage, "Downloading....", Toast.LENGTH_LONG).show()
                        //setwall(resource)
                        //setlockl(resource)
                        saveImage(resource)
                    }

                    private fun saveImage(image: Bitmap): String? {
                        //Toast.makeText(this@ViewImage, "Downloading,,,,", Toast.LENGTH_LONG).show()
                        //val rr = ('a'..'z').random()
                        var savedImagePath: String? = null

                        //Toast.makeText(this@ViewImage, t, Toast.LENGTH_LONG).show()
                        val imageFileName = "JPEG_" + t + ".jpg"
                        val storageDir = File(
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                            "/" + "Imago"
                        )
                        var success = true
                        if (!storageDir.exists()) {
                            success = storageDir.mkdirs()
                        }
                        if (success) {
                            val imageFile = File(storageDir, imageFileName)
                            savedImagePath = imageFile.absolutePath
                            try {
                                val fOut = FileOutputStream(imageFile)
                                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
                                fOut.close()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                            // Add the image to the system gallery
                            //galleryAddPic(savedImagePath)
                            if (status == "wallpaper") {
                                Toast.makeText(context, "Setting As Wallpaper", Toast.LENGTH_LONG).show()
                                setwall(image, imageFile)
                            } else if (status == "lock") {
                                Toast.makeText(context, "Setting As LockScreen", Toast.LENGTH_LONG).show()
                                setlock(image, imageFile)
                            } else {
                                galleryAddPic(savedImagePath)
                            }
                            //Toast.makeText(this@ViewImage, savedImagePath.toString(), Toast.LENGTH_LONG).show()
                        }
                        //setwall(image)
                        return savedImagePath
                    }


                    private fun galleryAddPic(imagePath: String) {
                        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                        val f = File(imagePath)
                        val contentUri = Uri.fromFile(f)
                        mediaScanIntent.setData(contentUri)
                        context.sendBroadcast(mediaScanIntent)
                    }

                    private fun setwall(wall: Bitmap, imageFile: File) {
                        val wallpaperManager = WallpaperManager.getInstance(context)
                        try {
                            wallpaperManager.setBitmap(wall)
                            val fOut = FileOutputStream(imageFile)
                            imageFile.delete()
                            fOut.close()
                            Toast.makeText(context, "Set As Wallpaper", Toast.LENGTH_LONG).show()

                        } catch (ex: IOException) {
                            ex.printStackTrace()
                        }
                    }

                    private fun setlock(lock: Bitmap, imageFile: File) {
                        val lockmanager =
                            WallpaperManager.getInstance(context) //WallpaperManager.getInstance(p!!)
                        try {
                            lockmanager.setBitmap(lock, null, true, WallpaperManager.FLAG_LOCK)
                            val fOut = FileOutputStream(imageFile)
                            imageFile.delete()
                            fOut.close()
                            Toast.makeText(context, "Set As Lockscreen", Toast.LENGTH_LONG).show()
                        } catch (ex: IOException) {
                            ex.printStackTrace()
                        }
                    }

//                    fun onResourceReady(resource:Bitmap, glideAnimation:GlideAnimation) {
//                        saveImage(resource, position)
//                    }
                })

        return params.size
    }

//    override fun onProgressUpdate(vararg values: Int?) {
//        progress_bar.progress = values[0]!!
//        //Thread.sleep(values[0]!!.toLong())
//    }

    override fun onPostExecute(result: Int?) {
        mAlert!!.dismiss()
        callback.onfinish(result)
    }
}