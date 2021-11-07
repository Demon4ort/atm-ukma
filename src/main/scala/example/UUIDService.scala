package example

import java.util.UUID

trait UUIDService {

  val uuidService: UUIDService

  class UUIDService {
    def genUUID: String = UUID.randomUUID.toString
  }
}
