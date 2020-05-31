package aso.mo.asoplayer.main.common.network


import android.net.TrafficStats
import java.io.IOException
import java.net.InetAddress
import java.net.Socket
import javax.net.SocketFactory

/**
 * Original source: https://github.com/square/okhttp/blob/master/okhttp/src/test/java/okhttp3/DelegatingSocketFactory.java
 * A [SocketFactory] that delegates calls. Sockets can be configured after creation by
 * overriding [.configureSocket].
 */
open class DelegatingSocketFactory(private val delegate: SocketFactory) : SocketFactory() {

    @Throws(IOException::class)
    override fun createSocket(): Socket {
        val socket = delegate.createSocket()
        return configureSocket(socket)
    }

    @Throws(IOException::class)
    override fun createSocket(host: String, port: Int): Socket {
        val socket = delegate.createSocket(host, port)
        return configureSocket(socket)
    }

    @Throws(IOException::class)
    override fun createSocket(
        host: String, port: Int, localAddress: InetAddress,
        localPort: Int
    ): Socket {
        val socket = delegate.createSocket(host, port, localAddress, localPort)
        return configureSocket(socket)
    }

    @Throws(IOException::class)
    override fun createSocket(host: InetAddress, port: Int): Socket {
        val socket = delegate.createSocket(host, port)
        return configureSocket(socket)
    }

    @Throws(IOException::class)
    override fun createSocket(
        host: InetAddress, port: Int, localAddress: InetAddress,
        localPort: Int
    ): Socket {
        val socket = delegate.createSocket(host, port, localAddress, localPort)
        return configureSocket(socket)
    }

    @Throws(IOException::class)
    protected open fun configureSocket(socket: Socket): Socket {
        // Just to overcome Strui
        TrafficStats.setThreadStatsTag(1)
        TrafficStats.tagSocket(socket)
        return socket
    }
}