package co.condorlabs.coologger.network.interceptor


import co.condorlabs.coologger.logger.Logger
import co.condorlabs.coologger.network.event.NetworkServiceInformation
import co.condorlabs.coologger.network.mapper.NetworkLogEventMapper
import co.condorlabs.coologger.network.requestprocessor.RequestProcessor
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class NetworkLoggerInterceptor(
    private val logger: Logger,
    private val eventMapper: NetworkLogEventMapper,
    private val requestProcessor: RequestProcessor
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val response = chain.proceed(request)

        val path = request.url.toUri().path

        val eventName = requestProcessor.getTrackingEvent(request) ?: return response

        logger.log(
            eventMapper.map(
                eventName,
                getNetworkServiceInformation(path, request, response)
            )
        )
        return response
    }

    private fun getNetworkServiceInformation(
        path: String,
        request: Request,
        response: Response
    ): NetworkServiceInformation {

        return NetworkServiceInformation(
            path,
            request.url.host,
            request.url.scheme,
            request.method,
            mapHeaderToMap(request.headers),
            mapHeaderToMap(response.headers),
            response.isSuccessful,
            response.code
        )
    }

    private fun mapHeaderToMap(headers: Headers): Map<String, String> {
        val map = mutableMapOf<String, String>()

        headers.forEach {
            map[it.first] = it.second
        }

        return map
    }
}
