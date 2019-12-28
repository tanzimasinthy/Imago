package com.example.space

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.CircularProgressDrawable
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Toast
import com.bumptech.glide.Glide
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_space_next.*
import kotlinx.android.synthetic.main.search_dialog_layout.view.*
import java.io.Reader

class SpaceNext : AppCompatActivity() {



    var status = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_space_next)
        supportActionBar?.hide()

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_space_next)

        init2()
    }


    fun init2()
    {

        val layout = findViewById<SwipeRefreshLayout>(R.id.pullToRefresh)
        layout.setOnRefreshListener(object: SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                status = true
                init2()
            }
        })
        layout.isRefreshing = false


        val k1 = intent.getStringExtra("title")
        val name = intent.getStringExtra("name")
        var k6 = intent.getStringExtra("all")
        var type = intent.getStringExtra("type")
        var username = intent.getStringExtra("username")


        if (status == false)
        {
            k6 = intent.getStringExtra("all")
        }
        else
        {
            var ty = type.toLowerCase()
            var ran = (1..20).random()
            k6 = "https://api.unsplash.com/photos/search?client_id=a4f7a39dd975fe7ae4dc626ac798912ce889ff417d1d54dfe2b9222f2a1a4b62&page=${ran}&per_page=21&query=${ty}"

        }


        //Toast.makeText(this, k1, Toast.LENGTH_LONG).show()

        val circularProgressDrawable = CircularProgressDrawable(this)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        Glide.with(this).load(k1).centerCrop().placeholder(circularProgressDrawable).into(img_top)

        //Toast.makeText(this, name, Toast.LENGTH_LONG).show()

        var tf = Typeface.createFromAsset(assets, "Striverx.ttf")

        next_title.text = type.toString()
        next_title.typeface = tf

        next_bottom.text = name.toString()
        next_bottom.typeface = tf

        next_bottom.setOnClickListener()
        {
            var string = "https://www.unsplash.com/@${username}"

            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(string)))
        }




        btn_float.setOnClickListener()
        {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.search_dialog_layout, null)
            val mBuilder = android.app.AlertDialog.Builder(this).setView(mDialogView)
            //Glide.with(p!!).load(data.urls.regular).centerCrop().into(mDialogView.dialog_img)
            val mAlert = mBuilder.show()
            mDialogView.search_btn.setOnClickListener()
            {
                val k = mDialogView.search_text.text.toString()
                if (k.length<2)
                {
                    Toast.makeText(this, "Enter Valid Search", Toast.LENGTH_LONG).show()
                }
                else
                {
                    //Toast.makeText(this, k, Toast.LENGTH_LONG).show()

                    val random = (1..20).random()

                    "https://api.unsplash.com/photos/search?client_id=a4f7a39dd975fe7ae4dc626ac798912ce889ff417d1d54dfe2b9222f2a1a4b62&page=${random}&per_page=4&query=${k}"
                        .httpGet()
                        .responseObject(MainActivity.Data.Deserializer())
                        {
                                request, response, result ->
                            when(result)
                            {
                                is Result.Failure ->
                                {
                                    val ex = result.getException()
                                }
                                is Result.Success ->
                                {
                                    var data = result.get()

                                    if (data.isEmpty())
                                    {
                                        Toast.makeText(this, "No Search Result Found" , Toast.LENGTH_LONG).show()
                                    }
                                    else
                                    {
                                        startActivity(Intent(this, Search::class.java).putExtra("search",k).putExtra("user","1"))
                                    }
                                }
                            }
                        }




                    //startActivity(Intent(this, Search::class.java).putExtra("search",k).putExtra("user","1"))
                }
            }
        }


        //next_bottom.text = name.toString()

        //Toast.makeText(this, k6.toString(), Toast.LENGTH_LONG).show()

        k6
            .httpGet()
            .responseObject(Data.Deserializer())
            {request, response, result ->

                when(result)
                {
                    is Result.Failure ->
                    {
                        val ex = result.getException()
                    }
                    is Result.Success ->
                    {
                        val data = result.get()

                        //Toast.makeText(this, data[2].urls.small.toString(),Toast.LENGTH_LONG).show()

                        val recyclerView = findViewById<RecyclerView>(R.id.recycler2)
                        recyclerView.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

                        val adapters = SpaceAdapter_2(data)

                        recyclerView.adapter = adapters
                    }

                }
            }
    }






    data class Data(
        var alt_description: Any,
        var categories: List<Any>,
        var color: String,
        var created_at: String,
        var current_user_collections: List<Any>,
        var description: Any,
        var height: Int,
        var id: String,
        var liked_by_user: Boolean,
        var likes: Int,
        var links: Links,
        var sponsored: Boolean,
        var sponsored_by: Any,
        var sponsored_impressions_id: Any,
        var updated_at: String,
        var urls: Urls,
        var user: User,
        var width: Int
    )
    {
        class Deserializer : ResponseDeserializable<Array<Data>> {
            override fun deserialize(content: String): Array<Data>? = Gson().fromJson(content, Array<Data>::class.java)!!
        }

        class ListDeserializer : ResponseDeserializable<List<Data>> {
            override fun deserialize(reader: Reader): List<Data> {
                val type = object : TypeToken<List<Data>>() {}.type
                return Gson().fromJson(reader, type)
            }
        }
    }

    data class Links(
        var download: String,
        var download_location: String,
        var html: String,
        var self: String
    )

    data class Urls(
        var full: String,
        var raw: String,
        var regular: String,
        var small: String,
        var thumb: String
    )

    data class User(
        var accepted_tos: Boolean,
        var bio: String,
        var first_name: String,
        var id: String,
        var instagram_username: String,
        var last_name: String,
        var links: LinksX,
        var location: String,
        var name: String,
        var portfolio_url: String,
        var profile_image: ProfileImage,
        var total_collections: Int,
        var total_likes: Int,
        var total_photos: Int,
        var twitter_username: String,
        var updated_at: String,
        var username: String
    )

    data class LinksX(
        var followers: String,
        var following: String,
        var html: String,
        var likes: String,
        var photos: String,
        var portfolio: String,
        var self: String
    )

    data class ProfileImage(
        var large: String,
        var medium: String,
        var small: String
    )
}

