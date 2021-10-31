package com.example.news

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_news.*
import org.json.JSONArray

class MainActivity : AppCompatActivity(), NewsItemcClicked {

    var Newsurl: String? = null

    private lateinit var mAdapter: NewsListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchdata()

        mAdapter = NewsListAdapter(this)

        recyclerView.adapter = mAdapter

    }

    private fun fetchdata() {
        val url = "https://saurav.tech/NewsAPI/top-headlines/category/health/in.json"
        val jsonObjectRequest = object : JsonObjectRequest(

            Request.Method.GET, url, null,
            Response.Listener {
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()

                for (i in 0 until newsJsonArray.length()) {
                    val newsjsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsjsonObject.getString("title"),
                        newsjsonObject.getString("author"),
                        newsjsonObject.getString("url"),
                        newsjsonObject.getString("urlToImage")
                    )

                    newsArray.add(news)
                    Newsurl = news.url


                }

                mAdapter.updatenews(newsArray)



            },
            Response.ErrorListener {

            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String>? {
                val headers = HashMap<String, String>()
                //headers.put("Content-Type", "application/json");
                headers["key"] = "Value"
                return headers
            }
        }

        // Access the RequestQueue through your singleton class.
        MySingleton.getintance(this).addToRequestQueue(jsonObjectRequest)

    }


      override fun onItemClicked(item: News) {

        val builder = CustomTabsIntent.Builder()
        val CustomTabsIntent = builder.build()
        CustomTabsIntent.launchUrl(this, Uri.parse(item.url));
    }

    fun shareButton(view: View) {

        val intent = Intent(Intent.ACTION_SEND)
         intent.type="text/plain"

        intent.putExtra(Intent.EXTRA_TEXT, "Check this news $Newsurl")

        val chooser = Intent.createChooser(intent, "share this via ...")
        startActivity(chooser)

    }


}


