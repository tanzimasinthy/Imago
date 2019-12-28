package com.example.space

import android.content.Context
import android.content.Intent
import android.support.v4.widget.CircularProgressDrawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.dialoug_space.view.*
import java.io.IOException
import java.io.Reader

class SpaceAdapter_2(val listdata: Array<SpaceNext.Data>):RecyclerView.Adapter<SpaceAdapter_2.ViewHolder>()
{
    var p:Context? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.list_layout_2,p0,false)
        p=p0.context
        return SpaceAdapter_2.ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listdata.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val data = listdata[p1]

        val circularProgressDrawable = CircularProgressDrawable(p!!)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        Glide.with(p!!).load(data.urls.small).centerCrop().placeholder(circularProgressDrawable).into(p0.grid_img)

        p0.grid_img.setOnClickListener()
        {
            //Toast.makeText(p, data.urls.small.toString(), Toast.LENGTH_LONG).show()

            val t = "By " + data.user.first_name+" "+data.user.last_name

            p!!.startActivity(Intent(p, ViewImage::class.java).putExtra("image", data.urls.regular).putExtra("image_full",data.urls.full).putExtra("name",t).putExtra("download", data.links.download).putExtra("username", data.user.username))

//            val mDialogView = LayoutInflater.from(p).inflate(R.layout.dialoug_space, null)
//            val mBuilder = android.app.AlertDialog.Builder(p).setView(mDialogView)
//            Glide.with(p!!).load(data.urls.regular).centerCrop().into(mDialogView.dialog_img)
//            val mAlert = mBuilder.show()
        }
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
        val grid_img = itemView.findViewById(R.id.img_grid) as ImageView
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