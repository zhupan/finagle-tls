package com.twitter.finagle.example.tls

import com.twitter.finagle.builder.ClientBuilder
import java.net.InetSocketAddress
import com.twitter.finagle.Service
import javax.net.ssl.{TrustManagerFactory, KeyManagerFactory, SSLContext}
import java.security.KeyStore
import java.io.FileInputStream

object TLSClient {
  def main(args: Array[String]) {
    val client: Service[String, String] = ClientBuilder()
      .codec(StringCodec)
      .hosts(new InetSocketAddress(8080))
      .hostConnectionLimit(1)
//      .tls(createSslContext)
      .tlsWithoutValidation()
      .build()


    client("Hello,How are you?\n") onSuccess {
      result => println("Received result asynchronously: " + result)
    } onFailure {
      error => error.printStackTrace()
    } ensure {
      client.close()
    }
  }

  private def createSslContext = {
    val sslContext = SSLContext.getInstance("TLS")
    val keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
    val trustManagerFactory = TrustManagerFactory.getInstance("SunX509")
    val keyStore = KeyStore.getInstance("JKS")
    val trustKeyStore = KeyStore.getInstance("JKS")
    keyStore.load(new FileInputStream("/Users/zhupan/github/finagle-tls-example/src/main/resources/ssl/client.key"), "derbysoft".toCharArray())
    trustKeyStore.load(new FileInputStream("/Users/zhupan/github/finagle-tls-example/src/main/resources/ssl/client.crt"), "derbysoft".toCharArray())
    keyManagerFactory.init(keyStore, "derbysoft".toCharArray())
    trustManagerFactory.init(trustKeyStore)
    sslContext.init(keyManagerFactory.getKeyManagers, trustManagerFactory.getTrustManagers, null)
    sslContext
  }
}
