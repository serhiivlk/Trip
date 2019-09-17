package com.serhiiv.trip

import android.util.Log
import com.serhiiv.trip.utils.Logger
import timber.log.Timber as TimberLib

object LoggerPrinter {
    class Timber : Logger.Printer {
        override fun log(priority: Int, tag: String, message: String, t: Throwable?) {
            when (priority) {
                Logger.DEBUG -> TimberLib.tag(tag).log(Log.DEBUG, t, message)
                Logger.ERROR -> TimberLib.tag(tag).log(Log.ERROR, t, message)
                Logger.INFO -> TimberLib.tag(tag).log(Log.INFO, t, message)
                Logger.WARN -> TimberLib.tag(tag).log(Log.WARN, t, message)
                else -> TimberLib.tag(tag).log(priority, t, message)
            }
        }
    }
}
