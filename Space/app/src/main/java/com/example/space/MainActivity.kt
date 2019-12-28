package com.example.space

import android.Manifest
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kotlinpermissions.KotlinPermissions
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.exit_layout.view.*
import kotlinx.android.synthetic.main.search_dialog_layout.view.*
import kotlinx.android.synthetic.main.search_dialog_layout.view.search_text
import java.io.Reader

class MainActivity : AppCompatActivity() {

    var pressTime:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)
        //myView.typeface = myCustomFont


        init1()
        //init2()
        //init4()
        //permission()
    }

    override fun onBackPressed() {
        if (pressTime +2000 > System.currentTimeMillis())
        {
            super.onBackPressed()
        }else
        {
            //Toast.makeText(this, "Press Back Again To Exit", Toast.LENGTH_LONG).show()

            val mDialogView = LayoutInflater.from(this).inflate(R.layout.exit_layout, null)
            val mBuilder = android.app.AlertDialog.Builder(this).setView(mDialogView)
            val mAlert = mBuilder.show()
            mDialogView.tik_btn.setOnClickListener()
            {
                finishAffinity()
            }
            mDialogView.cross_btn.setOnClickListener()
            {
                mAlert.dismiss()
            }
        }
    }

    fun init1()
    {
        KotlinPermissions.with(this) // where this is an FragmentActivity instance
            .permissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .onAccepted { permissions ->
                init4()
            }
            .onDenied { permissions ->
                init1()
            }
            .onForeverDenied { permissions ->
                init1()
            }
            .ask()
    }

    fun init4()
    {
        //Toast.makeText(this, "enter" , Toast.LENGTH_SHORT).show()

        val layout = findViewById<SwipeRefreshLayout>(R.id.pullToRefresh)
        layout.setOnRefreshListener(object: SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                init4()
            }
        })
        layout.setRefreshing(false)

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
                        .responseObject(Data.Deserializer())
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
                                    val data = result.get()
                                    if (data.isEmpty())
                                    {
                                        Toast.makeText(this, "No Search Result Found" , Toast.LENGTH_LONG).show()
                                    }
                                    else
                                        startActivity(Intent(this, Search::class.java).putExtra("search",k).putExtra("user","1"))


                                    //startActivity(Intent(this, Search::class.java).putExtra("search",k).putExtra("user","1"))

                                }
                            }
                        }




                    //startActivity(Intent(this, Search::class.java).putExtra("search",k).putExtra("user","1"))
                }
            }
        }

        var data_my2 = ArrayList<myData2>()
        var data_my1 = ArrayList<myData1>()

        var random = (1..10).random()

        //Toast.makeText(this, random.toString(), Toast.LENGTH_LONG).show()

        data_my1.add(myData1("https://api.unsplash.com/photos/search?client_id=a4f7a39dd975fe7ae4dc626ac798912ce889ff417d1d54dfe2b9222f2a1a4b62&page=${random}&per_page=21&query=astrophotography", "Astrophotography"))
        data_my1.add(myData1("https://api.unsplash.com/photos/search?client_id=a4f7a39dd975fe7ae4dc626ac798912ce889ff417d1d54dfe2b9222f2a1a4b62&page=${random}&per_page=21&query=art","Art"))
        data_my1.add(myData1("https://api.unsplash.com/photos/search?client_id=a4f7a39dd975fe7ae4dc626ac798912ce889ff417d1d54dfe2b9222f2a1a4b62&page=${random}&per_page=21&query=sky","Sky"))
        data_my1.add(myData1("https://api.unsplash.com/photos/search?client_id=a4f7a39dd975fe7ae4dc626ac798912ce889ff417d1d54dfe2b9222f2a1a4b62&page=${random}&per_page=21&query=stars","Stars"))
        data_my1.add(myData1("https://api.unsplash.com/photos/search?client_id=a4f7a39dd975fe7ae4dc626ac798912ce889ff417d1d54dfe2b9222f2a1a4b62&page=${random}&per_page=21&query=material","Material"))
        data_my1.add(myData1("https://api.unsplash.com/photos/search?client_id=a4f7a39dd975fe7ae4dc626ac798912ce889ff417d1d54dfe2b9222f2a1a4b62&page=${random}&per_page=21&query=love","Love"))
        data_my1.add(myData1("https://api.unsplash.com/photos/search?client_id=a4f7a39dd975fe7ae4dc626ac798912ce889ff417d1d54dfe2b9222f2a1a4b62&page=${random}&per_page=21&query=sea","Sea"))
        data_my1.add(myData1("https://api.unsplash.com/photos/search?client_id=a4f7a39dd975fe7ae4dc626ac798912ce889ff417d1d54dfe2b9222f2a1a4b62&page=${random}&per_page=21&query=nature","Nature"))
        data_my1.add(myData1("https://api.unsplash.com/photos/search?client_id=a4f7a39dd975fe7ae4dc626ac798912ce889ff417d1d54dfe2b9222f2a1a4b62&page=${random}&per_page=21&query=street","Street"))
        data_my1.add(myData1("https://api.unsplash.com/photos/search?client_id=a4f7a39dd975fe7ae4dc626ac798912ce889ff417d1d54dfe2b9222f2a1a4b62&page=${random}&per_page=21&query=cloud","Cloud"))

        data_my1.forEach {

            it.url1
                ?.httpGet()
                ?.responseObject(Data.Deserializer()){request, response, result ->
                    when(result)
                    {
                        is Result.Failure ->
                        {
                            val ex = result.getException()
                            //Toast.makeText(p, "failed",Toast.LENGTH_LONG).show()
                            //Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show()
                        }
                        is Result.Success ->
                        {
                            val data= result.get()
                            var kt = data[random].user.first_name+" "+data[random].user.last_name
                            data_my2.add(myData2(data[random].urls.regular,it.type,it.url1,kt, data[random].user.username))//,data[1].user.first_name,data[1].user.last_name,data[1].description.toString()))
//                        my_text.text = data[4].description.toString()
//                        Glide.with(this).load(data[4].urls.regular).into(img_1)
                            //Toast.makeText(this, kt,Toast.LENGTH_LONG).show()


                            val recyclerView = findViewById<RecyclerView>(R.id.recycler)
                            recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
                            val adapters = SpaceAdapter(data_my2)
                            recyclerView.adapter = adapters
                        }
                    }
                }

        }


        if (data_my2.isNotEmpty())
        {
            val recyclerView = findViewById<RecyclerView>(R.id.recycler)
            recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
            val adapters = SpaceAdapter(data_my2)
            recyclerView.adapter = adapters
        }
//        else
//            Toast.makeText(this, "error", Toast.LENGTH_LONG).show()

    }

    data class  myData1(var url1:String? = null, var type:String? = null)
    data class  myData2(
        var url1: String? = null,
        var type: String? = null,
        var url2: String? = null,
        var fullname: String?,
        var username: String
    )


    fun init()
    {
        "https://api.unsplash.com/photos/?client_id=a4f7a39dd975fe7ae4dc626ac798912ce889ff417d1d54dfe2b9222f2a1a4b62&page=1&query=sky"
            .httpGet()
            .responseObject(Data.Deserializer()){ request, response, result ->

                when(result)
                {
                    is Result.Failure ->
                    {
                        val ex = result.getException()
                        //Toast.makeText(p, "failed",Toast.LENGTH_LONG).show()
                        Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show()
                    }
                    is Result.Success ->
                    {
                        var data= result.get()
//                        my_text.text = data[4].description.toString()
//                        Glide.with(this).load(data[4].urls.regular).into(img_1)
                    }
                }
            }
    }

    fun init2()
    {
        val data_my = ArrayList<myData>()

        data_my.add(myData("https://api.unsplash.com/photos/search?client_id=a4f7a39dd975fe7ae4dc626ac798912ce889ff417d1d54dfe2b9222f2a1a4b62&page=2&per_page=21&query=astrophotography", "Astrophotography"))
        data_my.add(myData("https://api.unsplash.com/photos/search?client_id=a4f7a39dd975fe7ae4dc626ac798912ce889ff417d1d54dfe2b9222f2a1a4b62&page=2&per_page=21&query==art","art"))
        data_my.add(myData("https://api.unsplash.com/photos/search?client_id=a4f7a39dd975fe7ae4dc626ac798912ce889ff417d1d54dfe2b9222f2a1a4b62&page=2&per_page=21&query=sky","sky"))
        data_my.add(myData("https://api.unsplash.com/photos/search?client_id=a4f7a39dd975fe7ae4dc626ac798912ce889ff417d1d54dfe2b9222f2a1a4b62&page=2&per_page=21&query=stars","stars"))
        data_my.add(myData("https://api.unsplash.com/photos/search?client_id=a4f7a39dd975fe7ae4dc626ac798912ce889ff417d1d54dfe2b9222f2a1a4b62&page=2&per_page=21&query=material","material"))
        data_my.add(myData("https://api.unsplash.com/photos/search?client_id=a4f7a39dd975fe7ae4dc626ac798912ce889ff417d1d54dfe2b9222f2a1a4b62&page=2&per_page=21&query=love","love"))
        data_my.add(myData("https://api.unsplash.com/photos/search?client_id=a4f7a39dd975fe7ae4dc626ac798912ce889ff417d1d54dfe2b9222f2a1a4b62&page=2&per_page=21&query=sea","sea"))
        data_my.add(myData("https://api.unsplash.com/photos/search?client_id=a4f7a39dd975fe7ae4dc626ac798912ce889ff417d1d54dfe2b9222f2a1a4b62&page=2&per_page=21&query=nature","nature"))
        data_my.add(myData("https://api.unsplash.com/photos/search?client_id=a4f7a39dd975fe7ae4dc626ac798912ce889ff417d1d54dfe2b9222f2a1a4b62&page=2&per_page=21&query=street","street"))
        data_my.add(myData("https://api.unsplash.com/photos/search?client_id=a4f7a39dd975fe7ae4dc626ac798912ce889ff417d1d54dfe2b9222f2a1a4b62&page=2&per_page=21&query=cloud","cloud"))

//        val recyclerView = findViewById<RecyclerView>(R.id.recycler)
//        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayout.VERTICAL,false)
//
//        val adapters = SpaceAdapter(data_my)
//        recyclerView.adapter = adapters

    }

    open class myData(var url: String? = null,var name: String? = null)



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
