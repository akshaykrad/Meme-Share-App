package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var currentImageUrl= ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
    }

    private fun loadMeme(){
        progBar.visibility = View.VISIBLE
        //Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            { response ->
                currentImageUrl = response.getString("url")
                Glide.with(this).load(currentImageUrl).listener(object: RequestListener<Drawable>{

                    override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                    ): Boolean {
                        progBar.visibility = View.GONE
                        return false;
                    }

                    override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                    ): Boolean {
                        progBar.visibility = View.GONE
                        return false;
                    }
                }).into(img)
            },
            {
                Toast.makeText(this,"Something went Wrong",Toast.LENGTH_SHORT).show()
            }
        )
        //Add the request to RequestQueue.
        queue.add(jsonObjectRequest)
    }
    fun nextMeme(view : View){
        loadMeme()
    }
    fun share(view : View){
        val inten = Intent(Intent.ACTION_SEND)
        inten.type="text/plain"
        inten.putExtra(Intent.EXTRA_TEXT,"Arey bhai ye meme dekh $currentImageUrl")
        val chooser = Intent.createChooser(inten,"Share this meme using ..")
        startActivity(chooser)
    }
}