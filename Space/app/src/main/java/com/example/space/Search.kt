package com.example.space

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.CircularProgressDrawable
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import com.bumptech.glide.Glide
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.search_dialog_layout.view.*

class Search : AppCompatActivity() {


    var status = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        supportActionBar?.hide()

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_search)

        init4()
    }

    fun init4()
    {
        var search = intent.getStringExtra("search")
        var user = intent.getStringExtra("user")
        var random = (1..20).random()

        val layout = findViewById<SwipeRefreshLayout>(R.id.pullToRefresh)
        layout.setOnRefreshListener(object: SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                status = true
                init4()
            }
        })
        layout.isRefreshing = false
        //var link = intent.getStringExtra("link")

        //Toast.makeText(this,search , Toast.LENGTH_LONG).show()
        //Toast.makeText(this, user, Toast.LENGTH_LONG).show()
        //Toast.makeText(this,link , Toast.LENGTH_LONG).show()

        if (status)
        {
            random = (1..20).random()
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
                                        finish()
                                    }
                                }
                            }
                        }




                    //startActivity(Intent(this, Search::class.java).putExtra("search",k).putExtra("user","1"))
                }
            }
        }




        if (user=="1")
        {
            var random = (1..20).random()
            var Link = "https://api.unsplash.com/photos/search?client_id=a4f7a39dd975fe7ae4dc626ac798912ce889ff417d1d54dfe2b9222f2a1a4b62&page=${random}&per_page=99&query=${search}"

            Link
                .httpGet()
                .responseObject(MainActivity.Data.Deserializer())
                {
                    request, response, result ->
                    when(result)
                    {
                        is Result.Failure ->
                        {
                            val er = result.getException()
                            Toast.makeText(this, "No Data Found", Toast.LENGTH_LONG).show()
                        }
                        is Result.Success ->
                        {
                            val data = result.get()

                            var t = "By "+data[random].user.first_name+" "+data[random].user.last_name
                            val circularProgressDrawable = CircularProgressDrawable(this)
                            circularProgressDrawable.strokeWidth = 5f
                            circularProgressDrawable.centerRadius = 30f
                            circularProgressDrawable.start()


                            var tf = Typeface.createFromAsset(assets, "Striverx.ttf")


                            Glide.with(this).load(data[random].urls.regular).placeholder(circularProgressDrawable).centerCrop().into(search_img)
                            search_title.text = search.toString()
                            search_title.typeface = tf

                            tsearch_bottom.text = t
                            tsearch_bottom.typeface = tf

                            var username = data[random].user.username

                            tsearch_bottom.setOnClickListener()
                            {
                                var string = "https://www.unsplash.com/@${username}"


                                if (username!="null") {
                                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(string)))
                                }

                            }

                            val recyclerView = findViewById<RecyclerView>(R.id.recycler4)
                            recyclerView.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)


                            val adapters = SpaceAdapter_4(data)
                            recyclerView.adapter = adapters
                        }
                    }
                }
        }
        else if(user.isNotEmpty())
        {
            ;
        }
    }
}
