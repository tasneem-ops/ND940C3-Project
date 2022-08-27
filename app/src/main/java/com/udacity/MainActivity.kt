package com.udacity

import android.animation.ObjectAnimator
import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

private enum class RadioButtonChecked {NULL,GLIDE,PROJECT, RETROFIT }
class MainActivity : AppCompatActivity() {

    private var downloadID: Long = 0
    private var radioButtonChecked = RadioButtonChecked.NULL
    private lateinit var notificationManager: NotificationManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var action: NotificationCompat.Action
    private var URL : String? = null
    private lateinit var customButton : View
    private lateinit var fileName : String
    private lateinit var loadingButton : LoadingButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        loadingButton = LoadingButton(applicationContext)
        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        customButton = findViewById<View>(R.id.custom_button)
        notificationManager = getSystemService(NotificationManager::class.java)
        createChannel(CHANNEL_ID, "Downloads")
        customButton.setOnClickListener {
            onDownloadButtonClicked(customButton)
            download()
            animateButton()
           // notificationManager.sendNotification("Started Downloading", applicationContext)
        }
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if(downloadID == id){
                notificationManager.sendNotification("Download Completed", applicationContext)
            }
        }
    }

    private fun download() {
        if (URL != null) {
            val request =
                DownloadManager.Request(Uri.parse(URL))
                    .setTitle(getString(R.string.app_name))
                    .setDescription(getString(R.string.app_description))
                    .setRequiresCharging(false)
                    .setAllowedOverMetered(true)
                    .setAllowedOverRoaming(true)

            val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            downloadID =
                downloadManager.enqueue(request)// enqueue puts the download request in the queue.
        }
        else {
            Toast.makeText(this, "Please Choose on Item To Download", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
//        private const val URL =
//            "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip"
        private const val CHANNEL_ID = "channelId"
        private const val NOTIFICATION_ID = 0
    }

    fun onRadioButtonChecked(view : View){
        if (view is RadioButton){
            when(view.getId()){
                R.id.radio_glide ->{
                    if (view.isChecked) {
                        radioButtonChecked = RadioButtonChecked.GLIDE
                        URL = "https://github.com/bumptech/glide"
                        fileName = getString(R.string.glide)
                    }
                }
                R.id.radio_prject ->{
                    if (view.isChecked) {
                        radioButtonChecked = RadioButtonChecked.PROJECT
                        URL ="https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter"
                        fileName = getString(R.string.loadapp)
                    }
                }
                R.id.radio_retrofit ->{
                    if (view.isChecked) {
                        radioButtonChecked = RadioButtonChecked.RETROFIT
                        URL = "https://github.com/square/retrofit"
                        fileName = getString(R.string.retrofit)
                    }
                }
                else ->{
                    radioButtonChecked = RadioButtonChecked.NULL
                }
            }
        }
    }

    fun onDownloadButtonClicked(view: View){
       loadingButton.buttonState = ButtonState.Loading
        loadingButton.x()
    }

    fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context){
        val contentIntent = Intent(applicationContext, DetailActivity::class.java)
        contentIntent.putExtra("file_name", fileName)
        contentIntent.putExtra("status", "Fail")
        val contentPendingIntent = PendingIntent.getActivity(
            applicationContext,
            NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val builder = NotificationCompat.Builder(
            applicationContext, CHANNEL_ID
        )
            .setSmallIcon(R.drawable.abc_vector_test)
            .setContentTitle(applicationContext
                .getString(R.string.notification_title))
            .setContentText(messageBody)
            .setContentIntent(contentPendingIntent)
            .setAutoCancel(true)

        notify(NOTIFICATION_ID, builder.build())
    }

    private fun createChannel(channelId: String, channelName: String) {
        // TODO: Step 1.6 START create a channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                // TODO: Step 2.4 change importance
                NotificationManager.IMPORTANCE_HIGH
            )// TODO: Step 2.6 disable badges for this channel
                .apply {
                    setShowBadge(false)
                }

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)

            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)

        }
    }

    fun animateButton(){
      //Nothing

    }

}
