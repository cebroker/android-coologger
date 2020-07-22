package co.condorlabs.coologger.network.mapper

import co.condorlabs.coologger.event.LogEvent
import co.condorlabs.coologger.network.event.NetworkServiceInformation

typealias NetworkServiceName = String

interface NetworkLogEventMapper {

    fun map(
        serviceName: NetworkServiceName,
        serviceInformation: NetworkServiceInformation
    ): LogEvent
}
