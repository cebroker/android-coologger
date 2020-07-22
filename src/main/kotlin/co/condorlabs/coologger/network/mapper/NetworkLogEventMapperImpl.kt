package co.condorlabs.coologger.network.mapper

import co.condorlabs.coologger.event.LogEvent
import co.condorlabs.coologger.network.event.NetworkLogEvent
import co.condorlabs.coologger.network.event.NetworkServiceInformation


class NetworkLogEventMapperImpl :
    NetworkLogEventMapper {

    override fun map(
        serviceName: NetworkServiceName,
        serviceInformation: NetworkServiceInformation
    ): LogEvent = NetworkLogEvent(serviceName, serviceInformation)
}
