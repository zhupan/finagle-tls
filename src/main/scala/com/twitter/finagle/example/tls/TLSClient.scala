package com.twitter.finagle.example.tls

import com.twitter.finagle.builder.ClientBuilder
import java.net.InetSocketAddress
import com.twitter.finagle.Service
import javax.net.ssl.{TrustManagerFactory, KeyManagerFactory, SSLContext}
import java.security.KeyStore
import java.io.{File, FileInputStream}

object TLSClient {
  def main(args: Array[String]) {
    val client: Service[String, String] = ClientBuilder()
      .codec(StringCodec)
      .hosts(new InetSocketAddress(8080))
      .hostConnectionLimit(1)
      .tls(createSslContext)
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
    val keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm)
    val trustManagerFactory = TrustManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm)
    val keyStore = KeyStore.getInstance(KeyStore.getDefaultType)
    val trustKeyStore = KeyStore.getInstance("JKS")
    val path = this.getClass.getResource("/").getPath + "ssl/"
    keyStore.load(new FileInputStream(path + "tclient.keystore"), "derbysoft".toCharArray())
    trustKeyStore.load(new FileInputStream(path + "tclient.keystore"), "derbysoft".toCharArray())
    keyManagerFactory.init(keyStore, "derbysoft".toCharArray())
    trustManagerFactory.init(trustKeyStore)
    val sslContext = SSLContext.getInstance("TLS")
    sslContext.init(keyManagerFactory.getKeyManagers, trustManagerFactory.getTrustManagers, null)
    sslContext
  }
}
