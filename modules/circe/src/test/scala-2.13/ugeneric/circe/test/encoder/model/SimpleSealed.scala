package ugeneric.circe.test.encoder.model

import io.circe.Encoder
import ugeneric.circe.UCirce

object SimpleSealed {
  sealed trait ParentTrait
  object ParentTrait {
    private implicit val decodeTest01: UCirce.OEncoder[Test01]   = implicit e => _.encode
    private implicit val decodeTest02: Encoder[Test02.type]      = UCirce.encodeCaseObject
    private implicit val decodeTest03: UCirce.OEncoder[Test03]   = implicit e => _.encode
    implicit val encodeParentTrait: UCirce.SEncoder[ParentTrait] = implicit e => _.encode
  }

  case class Test01(i1: String, i2: Int)    extends ParentTrait
  case object Test02                        extends ParentTrait
  case class Test03(i1: String, i2: String) extends ParentTrait
}
