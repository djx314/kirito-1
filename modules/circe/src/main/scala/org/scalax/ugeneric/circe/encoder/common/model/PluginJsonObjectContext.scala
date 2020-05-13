package org.scalax.ugeneric.circe.encoder.common.model

import asuna.{Context3, Plus3, ZsgTuple0}
import io.circe.Json
import org.scalax.ugeneric.circe.NameTranslator

object PluginJsonObjectContext extends Context3[PluginJsonObjectContent] {

  private val initObjectAppender: JsonObjectFieldAppender = new JsonObjectFieldAppender {
    override def append(data: List[(String, Json)]): List[(String, Json)] = data
  }

  private val jsonObjectAppenderAsunaTuple0: JsonObjectAppender[ZsgTuple0] = new JsonObjectAppender[ZsgTuple0] {
    override def getAppender(data: ZsgTuple0): JsonObjectFieldAppender = {
      initObjectAppender
    }
  }

  override def append[X1, X2, X3, Y1, Y2, Y3, Z1, Z2, Z3](x: PluginJsonObjectContent[X1, X2, X3], y: PluginJsonObjectContent[Y1, Y2, Y3])(
    plus: Plus3[X1, X2, X3, Y1, Y2, Y3, Z1, Z2, Z3]
  ): PluginJsonObjectContent[Z1, Z2, Z3] = new PluginJsonObjectContent[Z1, Z2, Z3] {
    override def appendField(name: Z3, p: Option[NameTranslator]): JsonObjectAppender[Z2] = {
      val appender1 = x.appendField(plus.takeHead3(name), p)
      val appender2 = y.appendField(plus.takeTail3(name), p)
      new JsonObjectAppender[Z2] {
        override def getAppender(data: Z2): JsonObjectFieldAppender = {
          val data1 = plus.takeHead2(data)
          val data2 = plus.takeTail2(data)
          new JsonObjectFieldAppender {
            override def append(m: List[(String, Json)]): List[(String, Json)] = appender2.getAppender(data2).append(appender1.getAppender(data1).append(m))
          }
        }
      }
    }
  }

  override val start: PluginJsonObjectContent[ZsgTuple0, ZsgTuple0, ZsgTuple0] = new PluginJsonObjectContent[ZsgTuple0, ZsgTuple0, ZsgTuple0] {
    override def appendField(name: ZsgTuple0, p: Option[NameTranslator]): JsonObjectAppender[ZsgTuple0] = {
      jsonObjectAppenderAsunaTuple0
    }
  }

}
