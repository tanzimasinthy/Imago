package com.example.space

import android.app.WallpaperManager
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Typeface
import android.net.Uri
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v4.widget.CircularProgressDrawable
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.activity_view_image.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ViewImage : AppCompatActivity(), CallBack {

    override fun onfinish(any: Any?) {
        //Toast.makeText(this,any.toString(), Toast.LENGTH_SHORT).show()
    }

    private var progres:ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_image)
        supportActionBar?.hide()

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_view_image)

        init6()
    }

    fun init6() {
        val image = intent.getStringExtra("image")
        var name = intent.getStringExtra("name")
        val download = intent.getStringExtra("download")
        var username = intent.getStringExtra("username")

        var t = download.toString()
        t = t.replace("/", "")
        t = t.replace("http", "")
        t = t.replace(":", "")
        t = t.replace("download", "_imago")
        t = t.replace(".", "")

        val circularProgressDrawable = CircularProgressDrawable(this)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.backgroundColor = 255
        circularProgressDrawable.start()

        var tf = Typeface.createFromAsset(assets, "Striverx.ttf")


        Glide.with(this).load(image).centerCrop().placeholder(circularProgressDrawable).into(imageFull)


        view_detail.text = name.toString()
        view_detail.typeface = tf

        unsplash.text = "unsplash.com"
        unsplash.typeface = tf

        unsplash.setOnClickListener()
        {
            var string = "https://www.unsplash.com/"

            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(string)))
        }

        view_detail.setOnClickListener()
        {
            var string = "https://www.unsplash.com/@${username}"

            if (!username.isNullOrEmpty()) {

                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(string)))
            }
        }


        btn_save.setOnClickListener()
        {
            Toast.makeText(this, "Downloading", Toast.LENGTH_LONG).show()

//            progres = findViewById(R.id.progressBar)
//
//            AsyncDownloadImage(this,this,"download").execute(download.toString())

            val mDialogView = LayoutInflater.from(this).inflate(R.layout.layout_progress, null)
            val mBuilder = android.app.AlertDialog.Builder(this).setView(mDialogView)
            //Glide.with(p!!).load(data.urls.regular).centerCrop().into(mDialogView.dialog_img)
            val mAlert = mBuilder.show()

            val circularProgressDrawable = CircularProgressDrawable(this)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.backgroundColor = 255
            circularProgressDrawable.start()


            AsyncTask.execute {
                Glide.with(this)
                    .asBitmap()
                    .load(download)
                    .centerCrop()
                    .placeholder(circularProgressDrawable)
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
                                galleryAddPic(savedImagePath)
                                Toast.makeText(this@ViewImage, savedImagePath.toString(), Toast.LENGTH_LONG).show()
                            }
                            //setwall(image)
                            return savedImagePath
                        }

                        private fun galleryAddPic(imagePath: String) {
                            val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                            val f = File(imagePath)
                            val contentUri = Uri.fromFile(f)
                            mediaScanIntent.setData(contentUri)
                            this@ViewImage.sendBroadcast(mediaScanIntent)
                        }
//                    fun onResourceReady(resource:Bitmap, glideAnimation:GlideAnimation) {
//                        saveImage(resource, position)
//                    }
                    })
            }

        }


        btn_wall.setOnClickListener()
        {
            Toast.makeText(this, "Setting Wallpaper", Toast.LENGTH_LONG).show()

            //AsyncDownloadImage(this,this,"wallpaper").execute(download.toString())



            val mDialogView = LayoutInflater.from(this).inflate(R.layout.layout_progress, null)
            val mBuilder = android.app.AlertDialog.Builder(this).setView(mDialogView)
            //Glide.with(p!!).load(data.urls.regular).centerCrop().into(mDialogView.dialog_img)
            val mAlert = mBuilder.show()
            AsyncTask.execute {
                Glide.with(this)
                    .asBitmap()
                    .load(download)
                    .fitCenter()
                    .placeholder(circularProgressDrawable)
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
                                Toast.makeText(this@ViewImage, "Set As Wallpaper", Toast.LENGTH_LONG).show()
                                setwall(image, imageFile)
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
                            this@ViewImage.sendBroadcast(mediaScanIntent)
                        }

                        private fun setwall(wall: Bitmap, imageFile: File) {
                            val wallpaperManager = WallpaperManager.getInstance(this@ViewImage)
                            try {
                                wallpaperManager.setBitmap(wall)
                                val fOut = FileOutputStream(imageFile)
                                imageFile.delete()
                                fOut.close()
                                Toast.makeText(this@ViewImage, "Set As Wallpaper", Toast.LENGTH_LONG).show()

                            } catch (ex: IOException) {
                                ex.printStackTrace()
                            }
                        }

//                    fun onResourceReady(resource:Bitmap, glideAnimation:GlideAnimation) {
//                        saveImage(resource, position)
//                    }
                    })
                //Toast.makeText(this@ViewImage, "Set As Wallpaper", Toast.LENGTH_LONG).show()
            }
        }

        btn_lock.setOnClickListener()
        {
            Toast.makeText(this, "Setting Lockscreen", Toast.LENGTH_LONG).show()

            //AsyncDownloadImage(this,this,"lock").execute(download.toString())



            val mDialogView = LayoutInflater.from(this).inflate(R.layout.layout_progress, null)
            val mBuilder = android.app.AlertDialog.Builder(this).setView(mDialogView)
            //Glide.with(p!!).load(data.urls.regular).centerCrop().into(mDialogView.dialog_img)
            val mAlert = mBuilder.show()
            AsyncTask.execute {
                Glide.with(this)
                    .asBitmap()
                    .load(download)
                    .centerCrop()
                    .placeholder(circularProgressDrawable)
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
                                Toast.makeText(this@ViewImage, "Set As Wallpaper", Toast.LENGTH_LONG).show()
                                //setwall(image, imageFile)
                                setlockl(image, imageFile)
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
                            this@ViewImage.sendBroadcast(mediaScanIntent)
                        }

                        private fun setlockl(lock: Bitmap, imageFile: File) {
                            val lockmanager =
                                WallpaperManager.getInstance(this@ViewImage) //WallpaperManager.getInstance(p!!)
                            try {
                                lockmanager.setBitmap(lock, null, true, WallpaperManager.FLAG_LOCK)
                                val fOut = FileOutputStream(imageFile)
                                imageFile.delete()
                                fOut.close()
                                Toast.makeText(this@ViewImage, "Set As Lockscreen", Toast.LENGTH_LONG).show()
                            } catch (ex: IOException) {
                                ex.printStackTrace()
                            }
                        }

//                    fun onResourceReady(resource:Bitmap, glideAnimation:GlideAnimation) {
//                        saveImage(resource, position)
//                    }
                    })
            }

        }
    }



//    inner class MyAsyncTask : AsyncTask<String, Int, Int>(){
//
//        //Override the doInBackground method
//        override fun doInBackground(vararg params: String?): Int {
//            val count:Int=params.size
//            var index=0
//            while (index<count){
//
//                //Log.d("Kotlin","In doInBackground Method and Total parameter passed is :$count " + "and processing $index with value: ${params[index]}")
//// Publish the progress
//                publishProgress(index+1)
//                //Sleeping for 1 seconds
//                Thread.sleep(1000)
//                index++
//            }
//
//            return count;
//
//        }
//        // Override the onProgressUpdate method to post the update on main thread
//        override fun onProgressUpdate(vararg values: Int?) {
//            super.onProgressUpdate(*values)
//            if(values[0]!=null) {
//                statusProgress?.progress = values[0] as Int
//                stringBuilder?.append("Publish post called with ${values[0]}\n")
//                statusText?.text = stringBuilder.toString()
//            }
//
//        }
//
//
//        // Setup the intial UI before execution of the background task
//        override fun onPreExecute() {
//
//            super.onPreExecute()
//
//            stringBuilder?.append("Async task started... \n In PreExecute Method \n")
//            statusText?.text = "${stringBuilder.toString()}"
//            progressBar.visibility=View.VISIBLE
//            progressBar.progress = 0
//            //Log.d("Kotlin","On PreExecute Method")
//
//        }
//        // Update the final status by overriding the OnPostExecute method.
//        override fun onPostExecute(result: Int?) {
//            super.onPostExecute(result)
//            stringBuilder?.append("Task Completed.\n")
//            statusText?.text = "${stringBuilder.toString()}"
//            //Log.d("Kotlin","On Post Execute and size of String is:$result")
//        }
//
//    }




}