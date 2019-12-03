package org.scalax.kirito.circe.decoder

import asuna.{Application2, Context2}
import asuna.macros.ByNameImplicit
import asuna.macros.single.SealedTag
import io.circe.Decoder

class SealedTraitSelector[P] {

  trait JsonDecoder[II, T] {
    def getValue(name: II, tran: T): Decoder[P]
  }

}

object SealedTraitSelector {

  def apply[T]: SealedTraitSelector[T] = value.asInstanceOf[SealedTraitSelector[T]]
  val value: SealedTraitSelector[Any]  = new SealedTraitSelector[Any]

  implicit def asunaCirceSealedDecoder[T, R](
    implicit t: ByNameImplicit[Decoder[R]]
  ): Application2[SealedTraitSelector[T]#JsonDecoder, SealedTag[R], String, R => T] = new Application2[SealedTraitSelector[T]#JsonDecoder, SealedTag[R], String, R => T] {
    override def application(context: Context2[SealedTraitSelector[T]#JsonDecoder]): SealedTraitSelector[T]#JsonDecoder[String, R => T] = {
      val con = SealedTraitSelector[T]
      new con.JsonDecoder[String, R => T] {
        override def getValue(name: String, tran: R => T): Decoder[T] = {
          Decoder.instance(j => j.get(name)(t.value).map(tran))
        }
      }
    }
  }

}
