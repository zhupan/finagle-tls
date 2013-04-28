package com.twitter.finagle.example.tls

import com.twitter.finagle.builder.ClientBuilder
import java.net.InetSocketAddress
import com.twitter.finagle.Service
import javax.net.ssl.{TrustManagerFactory, KeyManagerFactory, SSLContext}
import java.security.KeyStore
import java.io.{InputStream, File, FileInputStream}
/**
 * @author Panos Zhu
 *         Email panos.zhu@gmail.com
 */
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
    keyStore.load(SslFile.keyStoreStream, SslFile.clientPassword.toCharArray)
    trustKeyStore.load(SslFile.trustKeyStoreStream, SslFile.clientPassword.toCharArray)
    keyManagerFactory.init(keyStore, SslFile.clientPassword.toCharArray)
    trustManagerFactory.init(trustKeyStore)
    val sslContext = SSLContext.getInstance("TLS")
    sslContext.init(keyManagerFactory.getKeyManagers, trustManagerFactory.getTrustManagers, null)
    sslContext
  }
}
