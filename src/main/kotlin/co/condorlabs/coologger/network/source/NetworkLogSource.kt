package co.condorlabs.coologger.network.source

import co.condorlabs.coologger.event.LogSource

object NetworkLogSource : LogSource {
    override val name: String = NETWORK_LOG_SOURCE_NAME
}

const val NETWORK_LOG_SOURCE_NAME = "NETWORK_LOG_SOURCE"
