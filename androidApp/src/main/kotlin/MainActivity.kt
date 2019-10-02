package org.RetnoWidyanti.gifclone

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import kotlinx.coroutines.*
import org.gifLibrary.GiphyAPI

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private lateinit var adapter: GifAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val catGIFList: RecyclerView = findViewById(R.id.gif_list)
        val layoutManager = LinearLayoutManager(this)
        val pagerSnapHelper = PagerSnapHelper()
        catGIFList.layoutManager = layoutManager
        pagerSnapHelper.attachToRecyclerView(catGIFList)

        adapter = GifAdapter(this)
        catGIFList.adapter = adapter

        getCatGIFs {
            adapter.setResults(it)
        }
    }

    private fun getCatGIFs(callback: (List<String>) -> Unit) {
        MainScope().launch {
            async(Dispatchers.IO) {
                GiphyAPI().getCatGIFLinks(callback)
            }.await()
        }
    }
}
