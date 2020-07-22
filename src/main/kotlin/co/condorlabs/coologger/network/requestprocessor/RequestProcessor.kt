package co.condorlabs.coologger.network.requestprocessor

import okhttp3.Request

interface RequestProcessor {

    fun getTrackingEvent(request: Request): String?
}
