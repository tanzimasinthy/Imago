package com.example.space

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.support.v4.widget.CircularProgressDrawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Reader

class SpaceAdapter(val datalist: ArrayList<MainActivity.myData2>):RecyclerView.Adapter<SpaceAdapter.ViewHolder>() {

    var p:Context? = null
    var k1:String? = null
    var k2:String? = null
    var k3:String? = null
    var k6:String? = "test"
    //val type = Typeface.createFromAsset(this,"Striverx.ttf")




    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.space_layout, p0, false)
        p = p0.context
        return SpaceAdapter.ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val my_data = datalist[p1]

        var tf = Typeface.createFromAsset(p?.assets, "Striverx.ttf")


        //var k = my_data.url.toString()

        //p0.space_text.text = my_data.name.toString()
        //p0.space_text.text = my_data.url.toString()
        var k:String? = null

        k3 = my_data.type
        k6 = my_data.url2
        k1 = my_data.url1
        var name = "By"+" "+my_data.fullname

        val circularProgressDrawable = CircularProgressDrawable(p!!)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        Glide.with(p!!).load(my_data.url1).placeholder(circularProgressDrawable).centerCrop().into(p0.space_img)
        p0.space_text.text = my_data.type.toString()
        p0.space_text.typeface = tf
        //p0.space_desc.text = my_data.type.toString()
        p0.space_bottom.text = name
        p0.space_bottom.typeface = tf

        p0.space_bottom.setOnClickListener()
        {
            var string = "https://www.unsplash.com/@${my_data.username}"

            p!!.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(string)))
        }
        //p0.space_bottom.text = name
        //p0.space_desc.text = my_data.desc
//        my_data.url1
//            ?.httpGet()
//            ?.responseObject(Data.Deserializer())
//            { request, response, result ->
//                when(result)
//                {
//                    is Result.Failure ->
//                    {
//                        val ex = result.getException()
//                        try {
//                            Glide.with(p!!).load("http://goo.gl/gEgYUd").into(p0.space_img)
//                        }catch (e : IOException)
//                        {
//                            Toast.makeText(p, "exception", Toast.LENGTH_LONG).show()
//                        }
//                    }
//                    is Result.Success ->
//                    {
//                        val data = result.get()
//                        Glide.with(p!!).load(data[p1].urls.regular).centerCrop().into(p0.space_img)
//                        k1 = data[p1].urls.regular
//                        k2 = my_data.url
//                        k3 = my_data.name
//                        k6 = my_data.url
//
//                        p0.space_text.text = k3.toString()
//                    }
//
//                }
//
//            }

        p0.space_img.setOnClickListener()
        {
            p!!.startActivity(Intent(p,SpaceNext::class.java).putExtra("all", my_data.url2).putExtra("title",my_data.url1).putExtra("name",name).putExtra("type", my_data.type).putExtra("username", my_data.username))
        }
    }

    class ViewHolder(itemview: View):RecyclerView.ViewHolder(itemview)
    {
//        var space_img = itemview.findViewById(R.id.list_img) as ImageView
//        var space_text = itemview.findViewById(R.id.my_text) as TextView

        var space_img = itemview.findViewById(R.id.sp_image) as ImageView
        var space_text = itemview.findViewById(R.id.sp_text_top) as TextView
        var space_desc = itemview.findViewById(R.id.sp_text_desc) as TextView
        var space_bottom = itemview.findViewById(R.id.sp_text_bottom) as TextView
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





