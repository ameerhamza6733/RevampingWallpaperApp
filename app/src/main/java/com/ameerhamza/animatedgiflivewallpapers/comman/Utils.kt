package com.ameerhamza.animatedgiflivewallpapers.comman

import android.media.AudioManager
import android.media.ToneGenerator
import android.util.Log

object Utils {
    fun beep(){
        Log.d("Calls", "Beep")
        ToneGenerator(AudioManager.STREAM_MUSIC, 100).startTone(ToneGenerator.TONE_CDMA_PIP, 200)
    }
}