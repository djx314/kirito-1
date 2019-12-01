package org.scalax.kirito.circe.decoder

import asuna.macros.single.{DefaultValue, PropertyTag}
import asuna.macros.ByNameImplicit
import asuna.{Application3, Context3}
import io.circe._

trait JsonDecoderPro[T, II, D] extends Any {
  def to(name: II, defaultValue: D): Decoder[T]
}

object JsonDecoderPro {

  implicit def asunaCirceDecoder[T](
    implicit dd: ByNameImplicit[Decoder[T]]
  ): Application3[JsonDecoderPro, PropertyTag[T], T, String, DefaultValue[T]] = new Application3[JsonDecoderPro, PropertyTag[T], T, String, DefaultValue[T]] {
    override def application(context: Context3[JsonDecoderPro]): JsonDecoderPro[T, String, DefaultValue[T]] = new JsonDecoderPro[T, String, DefaultValue[T]] {
      override def to(name: String, defaultValue: DefaultValue[T]): Decoder[T] = {
        Decoder.instance { j =>
          defaultValue.value.map(s => Right(s)).getOrElse(j.get(name)(dd.value))
        }
      }
    }
  }

}
