package com.twitter.finagle.example.tls

import com.twitter.finagle.Service
import com.twitter.util.Future
import java.net.InetSocketAddress
import com.twitter.finagle.builder.ServerBuilder

object TLSServer {

  def main(args: Array[String]) {

    val service = new Service[String, String] {
      def apply(request: String) = {
        println(request)
        Future.value("I'm ok. " + request)
      }
    }

    ServerBuilder().codec(StringCodec).bindTo(new InetSocketAddress(8080))
      .tls("/Users/zhupan/github/finagle-tls-example/src/main/resources/ssl/server.crt", "/Users/zhupan/github/finagle-tls-example/src/main/resources/ssl/server.key")
      .name("TLSServer").build(service)
  }
}
