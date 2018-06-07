package com.example.sinb.cnn10reader

import android.content.Context
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import kotlin.coroutines.experimental.coroutineContext


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "Start Connect !!!!!")
        ScrapeDateTask(this, recycler_view).execute()
    }

    class ScrapeDateTask(val sContext: Context, val recycler_view: RecyclerView) : AsyncTask<Void, Void, Void>() {

        private val TAG = "ScrapeDataTask"
        var dataArrList = ArrayList<String>()

        override fun doInBackground(vararg params: Void?): Void? {
            Log.d(TAG, "doInBackground !!!!!")
            val doc : Document = Jsoup.connect("http://transcripts.cnn.com/TRANSCRIPTS/sn.html").maxBodySize(30000).get()
            val row : Elements = doc.select("div[class=cnnTransDate]")
            Log.d(TAG, row.text())
            dataArrList = ArrayList(row.text().split(", "))
            if (dataArrList[0] == "November 18")  dataArrList.removeAt(0)
            return null
        }

        override fun onPostExecute(result: Void?) {
            Log.d(TAG, "onPostExecute !!!!!")
            super.onPostExecute(result)
            val handler = Handler(Looper.getMainLooper())
            handler.post({
                recycler_view.layoutManager = LinearLayoutManager(sContext)
                recycler_view.adapter = RecyclerViewAdapter(dataArrList, sContext)
            })
        }
    }

}





