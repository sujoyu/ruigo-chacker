package jp.sujoyu.ruigoc

import com.atilika.kuromoji.ipadic.{Token, Tokenizer}
import edu.cmu.lti.jawjaw.JAWJAW

import scala.collection.JavaConverters._
import edu.cmu.lti.jawjaw.pobj.POS

object Main {
  def main(args: Array[String]): Unit = {
    val text = args match {
      case Array(t) => t
      case _ => println("error: 引数は1つだよ。")
        return
    }

    val posNameToId = Map(
      "名詞" -> POS.n,
      "動詞" -> POS.v
    )

    val tokenizer = new Tokenizer()
    val tokens = tokenizer.tokenize(text).asScala

    val targetTokens = tokens.filter(isTarget).toArray

    if (targetTokens.length < 10) {
      println("error: 名詞、動詞を11個以上書いて出直してきな！")
      return
    }

    val warnSpan = 5

    val result1 = for {
      i <- 0 until targetTokens.length - warnSpan
      j <- i to i + warnSpan
    } yield {
      (targetTokens(i),
        containsSynonym(targetTokens(i).getBaseForm,
          posNameToId(targetTokens(i).getPartOfSpeechLevel1),
          targetTokens(j).getBaseForm,
          posNameToId(targetTokens(j).getPartOfSpeechLevel1)))
    }

    val result2 = for {
      i <- targetTokens.length - warnSpan * 2 until targetTokens.length - warnSpan
      j <- i to i + warnSpan
    } yield {
      (targetTokens(j),
        containsSynonym(targetTokens(i).getBaseForm,
          posNameToId(targetTokens(i).getPartOfSpeechLevel1),
          targetTokens(j).getBaseForm,
          posNameToId(targetTokens(j).getPartOfSpeechLevel1)))
    }

    val result = result1 ++ result2

    val poss = result.collect {
      case (token, true) => Seq(token.getPosition, token.getSurface.length)
    }.distinct

    println(poss.map(_.mkString("\t")).mkString("\n"))
  }

  def isTarget(token: Token): Boolean = {
    token.getPartOfSpeechLevel1 == "名詞" || token.getPartOfSpeechLevel1 == "動詞"
  }

  def containsSynonym(word: String, pos: POS, target: String, tPos: POS): Boolean = {
    pos == tPos &&
      (JAWJAW.findHypernyms(word, pos).asScala ++
        JAWJAW.findHyponyms(word, pos).asScala ++ Seq(word)).toSeq.distinct.contains(target)
  }

}

