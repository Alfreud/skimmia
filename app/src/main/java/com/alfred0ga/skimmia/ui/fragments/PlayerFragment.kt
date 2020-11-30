package com.alfred0ga.skimmia.ui.fragments

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.alfred0ga.skimmia.R
import kotlinx.android.synthetic.main.fragment_player.*
import java.lang.Exception

class PlayerFragment : Fragment(R.layout.fragment_player) {
    private lateinit var mp: MediaPlayer
    private var totalTime: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mp = MediaPlayer.create(context, R.raw.se_te_nota)
        mp.isLooping = false
        mp.setVolume(0.5f, 0.5f)
        totalTime = mp.duration

        initiliseSeekbar()

        btn_play.setOnClickListener {
            if (mp.isPlaying) {
                // stop
                mp.pause()
                btn_play.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp)
            } else {
                // start
                mp.start()
                btn_play.setBackgroundResource(R.drawable.ic_pause_black_24dp)
            }
        }

        positionBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekbar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {
                        mp.seekTo(progress)
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {}

                override fun onStopTrackingTouch(p0: SeekBar?) {}
            }
        )

        Thread(Runnable {
            while (mp != null) {
                try {
                    var msg = Message()
                    msg.what = mp.currentPosition
                    handler.sendMessage(msg)
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {}
            }
        }).start()
    }

    private fun initiliseSeekbar() {
        positionBar.max = mp.duration

        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                try {
                    positionBar.progress = mp.currentPosition
                    handler.postDelayed(this, 1000)
                } catch (e: Exception) {
                    positionBar.progress = 0
                }

            }
        }, 0)

    }

    @SuppressLint("HndlerLeak")
    var handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            var currentPosition = msg.what

            var elapsedTime = createTimeLabel(currentPosition)
            elapsedTimeLabel.text = elapsedTime

            var remainingTime = createTimeLabel(totalTime - currentPosition)
            remainingTimeLabel.text = "-$remainingTime"
        }
    }

    fun createTimeLabel(time: Int): String {
        var timeLabel = ""
        var min = time / 1000 / 60
        var sec = time / 1000 % 60

        timeLabel = "$min:"
        if (sec < 10) timeLabel += "0"
        timeLabel += sec
        return timeLabel
    }
}

