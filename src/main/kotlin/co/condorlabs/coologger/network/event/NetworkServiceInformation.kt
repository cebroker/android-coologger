package co.condorlabs.coologger.network.event

class NetworkServiceInformation(
    val path: String,
    val host: String,
    val schema: String,
    val method: String,
    val requestHeaders: Map<String, String>,
    val responseHeaders: Map<String, String>,
    val isSuccess: Boolean,
    val responseCode: Int
)
