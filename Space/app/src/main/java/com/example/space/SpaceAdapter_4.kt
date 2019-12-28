package com.example.space

import android.content.Context
import android.content.Intent
import android.support.v4.widget.CircularProgressDrawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide

class SpaceAdapter_4(val listdata:Array<MainActivity.Data>):RecyclerView.Adapter<SpaceAdapter_4.ViewHolder>()
{

    var p:Context? = null


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.list_layout_2, p0, false)
        p=p0.context
        return SpaceAdapter_4.ViewHolder(v)
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

        Glide.with(p!!).load(data.urls.small).centerCrop().placeholder(circularProgressDrawable).into(p0.grid)

        p0.grid.setOnClickListener()
        {
            val t = data.user.first_name+" "+data.user.last_name
            p!!.startActivity(Intent(p,ViewImage::class.java).putExtra("image",data.urls.full).putExtra("image_full", data.urls.full).putExtra("name",t).putExtra("download", data.links.download).putExtra("username", data.user.username))
        }
    }

    class ViewHolder(item:View):RecyclerView.ViewHolder(item)
    {
        var grid = item.findViewById(R.id.img_grid) as ImageView
    }
}