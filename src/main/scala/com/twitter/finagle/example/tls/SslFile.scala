package com.twitter.finagle.example.tls

/**
 * @author Panos Zhu
 *         Email panos.zhu@gmail.com
 * @since 1.8
 */
object SslFile {

  val serverCrt = {
    val serverCrtPath = this.getClass.getResource("/").getPath + "ssl/temp/server.crt"
    CopyFile.copy(this.getClass.getResourceAsStream("/ssl/server.crt"), serverCrtPath)
    serverCrtPath
  }

  val serverKey = {
    val serverKeyPath = this.getClass.getResource("/").getPath + "ssl/temp/server.key"
    CopyFile.copy(this.getClass.getResourceAsStream("/ssl/server.key"), serverKeyPath)
    serverKeyPath
  }

  val keyStoreStream = this.getClass.getResourceAsStream("/ssl/tclient.keystore")

  val trustKeyStoreStream = this.getClass.getResourceAsStream("/ssl/tclient.keystore")

  val clientPassword = "derbysoft"

}
