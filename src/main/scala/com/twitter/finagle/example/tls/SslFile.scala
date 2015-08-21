package com.twitter.finagle.example.tls

/**
 * @author Panos Zhu
 *         Email panos.zhu@gmail.com
 */
object SslFile {

  private val sslFileDir = System.getProperty("java.io.tmpdir")

  val serverCrt = {
    val serverCrt = "/ssl/test/server.crt"
    val serverCrtPath = sslFileDir + serverCrt
    CopyFile.copy(this.getClass.getResourceAsStream(serverCrt), serverCrtPath)
    serverCrtPath
  }

  val serverKey = {
    val serverKey = "/ssl/test/server.key"
    val serverKeyPath = sslFileDir + serverKey
    CopyFile.copy(this.getClass.getResourceAsStream(serverKey), serverKeyPath)
    serverKeyPath
  }

  val keyStoreStream = this.getClass.getResourceAsStream("/ssl/test/tclient.keystore")

  val trustKeyStoreStream = this.getClass.getResourceAsStream("/ssl/test/tclient.keystore")

  val clientPassword = "changeit"

}
