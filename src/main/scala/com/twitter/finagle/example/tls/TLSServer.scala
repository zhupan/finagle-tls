package com.twitter.finagle.example.tls

import com.twitter.finagle.Service
import com.twitter.util.Future
import java.net.{URL, InetSocketAddress}
import com.twitter.finagle.builder.ServerBuilder
import java.io.File
/**
 * @author Panos Zhu
 *         Email panos.zhu@gmail.com
 */
object TLSServer {

  def main(args: Array[String]) {

    val service = new Service[String, String] {
      def apply(request: String) = {
        println(request)
        Future.value("I'm ok. " + request)
      }
    }

    ServerBuilder().codec(StringCodec).bindTo(new InetSocketAddress(8080))
      .tls(SslFile.serverCrt, SslFile.serverKey)
      .name("TLSServer").build(service)
  }
}
