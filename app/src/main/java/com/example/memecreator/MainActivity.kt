package com.example.memecreator

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.android.volley.VolleyError as VolleyError1

class MainActivity : AppCompatActivity() {
    var currentimage : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
    }
    private fun loadMeme() {
        val textView = findViewById<TextView>(R.id.text)
        val memeimage = findViewById<ImageView>(R.id.memeimage)
        val bar = findViewById<ProgressBar>(R.id.bar)
        bar.visibility = View.VISIBLE
// ...

// Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = 	"https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
        val jsonobject = JsonObjectRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->
                    val currentimage = response.getString("url")
                    Glide.with(this).load(currentimage).listener(object : RequestListener<Drawable> {
                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            bar.visibility = View.GONE
                            return false;
                        }
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            bar.visibility = View.GONE
                            return false
                        }
                    }).into(memeimage)
                },
                Response.ErrorListener {

                })

// Add the request to the RequestQueue.
        queue.add(jsonobject)

    }

    fun nextmeme(view: View) {
        loadMeme()
    }
    fun sharememe(view: View) {
        val intent  = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Hey check out this new meme $currentimage")
        val chooser = Intent.createChooser(intent,"Share this meme using ")
        startActivity(chooser)
    }
}