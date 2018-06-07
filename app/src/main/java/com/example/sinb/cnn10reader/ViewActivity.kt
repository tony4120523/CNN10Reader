package com.example.sinb.cnn10reader

import android.content.Context
import android.net.Uri
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.webkit.WebView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_view.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class ViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        val date = intent.getStringExtra("date")
        //val date = "2018 May 31"

        Log.d("ViewActivity", date)
        val aList = ArrayList<String>(date.split(" "))

        when (aList[1]) {
            "January" -> aList[1] = "01"
            "February" -> aList[1] = "02"
            "March" -> aList[1] = "03"
            "April" -> aList[1] = "04"
            "May" -> aList[1] = "05"
            "June" -> aList[1] = "06"
            "July" -> aList[1] = "07"
            "August" -> aList[1] = "08"
            "September" -> aList[1] = "09"
            "October" -> aList[1] = "10"
            "November" -> aList[1] = "11"
            "December" -> aList[1] = "12"
        }

        //val format2 = "0601"
        //val format3 = "1806/01"
        val format2 = aList[1] + aList[2]
        val format3 = aList[0].substring(2) + aList[1] + "/" + aList[2]

        when (aList[1].toInt()) {
            1, 2, 4, 6, 8, 9, 11 -> {
                if (aList[2].toInt() != 1) {
                    aList[2] = (aList[2].toInt() - 1).toString()
                } else {
                    aList[1] = (aList[1].toInt() - 1).toString()
                    aList[2] = "31"
                    if (aList[1] == "0")  aList[1] = "12"
                }
            }
            5, 7, 10, 12 -> {
                if (aList[2].toInt() != 1) {
                    aList[2] = (aList[2].toInt() - 1).toString()
                } else {
                    aList[1] = (aList[1].toInt() - 1).toString()
                    aList[2] = "30"
                }
            }
            3 -> {
                if (aList[2].toInt() != 1) {
                    aList[2] = (aList[2].toInt() - 1).toString()
                } else {
                    aList[1] = (aList[1].toInt() - 1).toString()
                    aList[2] = "28"
                }
            }
        }
        //val format1 = "2018/05/31"
        val format1 = aList[0] + "/" + String.format("%02d", aList[1].toInt()) + "/" +
                String.format("%02d", aList[2].toInt())

        videoView.setVideoURI(Uri.parse("http://podcasts.cnn.net/cnn/big/cnn10/" + format1 +
                "/ten-" + format2 + ".cnn_ios_1240.mp4"))
        videoView.start()

        ScrapeTranscriptTask(this, webView, format3).execute()
    }

    class ScrapeTranscriptTask(val sContext: Context, val webView: WebView, val str: String) : AsyncTask<Void, Void, Void>() {

        private val TAG = "ScrapeTranscriptTask"
        var dataArrList = ArrayList<String>()
        var transcript: String? = null

        override fun doInBackground(vararg params: Void?): Void? {
            Log.d(TAG, "doInBackground !!!!!")
            val doc : Document = Jsoup.connect("http://transcripts.cnn.com/TRANSCRIPTS/" + str + "/sn.01.html").get()
            val row : Elements = doc.select("P[class=cnnBodyText]")
            Log.d(TAG, row.text())
            transcript = row.toString()
            //dataArrList = ArrayList(row.text().split(", "))
            return null
        }

        override fun onPostExecute(result: Void?) {
            Log.d(TAG, "onPostExecute !!!!!")
            super.onPostExecute(result)
            val handler = Handler(Looper.getMainLooper())
            handler.post({
                webView.loadData(transcript, "text/html", null)
            })
        }
    }
}

