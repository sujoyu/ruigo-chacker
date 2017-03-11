package jp.sujoyu.ruigoc

import com.atilika.kuromoji.ipadic.{Token, Tokenizer}
import edu.cmu.lti.jawjaw.JAWJAW

import scala.collection.JavaConverters._
import edu.cmu.lti.jawjaw.pobj.POS

object Main {

  val posNameToId = Map(
      "名詞" -> POS.n,
      "動詞" -> POS.v
    )

  def main(args: Array[String]): Unit = {
    val (text, warnSpan) = args match {
      case Array(t, s) => (t, s.toInt)
      case _ => println("error: 引数は2つだよ。")
        return
    }

    val tokenizer = new Tokenizer()
    val tokens = tokenizer.tokenize(text).asScala

    val targetTokens = tokens.filter(isTarget).toArray

    if (targetTokens.length < warnSpan * 2) {
      println(s"error: 名詞、動詞を${warnSpan * 2 + 1}個以上書いて出直してきな！")
      return
    }

    val result = collection.mutable.Map.empty[Token, Int]
    var count = 0
    for {
      i <- 0 until (targetTokens.length - warnSpan)
      j <- i + 1 to (i + warnSpan)
      if containsSynonym(targetTokens(i).getBaseForm,
          targetTokens(i).getPartOfSpeechLevel1,
          targetTokens(j).getBaseForm,
          targetTokens(j).getPartOfSpeechLevel1)
    } {
        if (result.contains(targetTokens(i))) {
          val id = result(targetTokens(i))
          result(targetTokens(i)) = id
          result(targetTokens(j)) = id
        } else {
          result(targetTokens(i)) = count
          result(targetTokens(j)) = count
          count += 1
        }
    }

    val poss = result.map { case (token, id) =>
      Seq(token.getPosition, token.getSurface.length, id)
    }

    println(poss.map(_.mkString(",")).mkString("\n"))
  }

  def isTarget(token: Token): Boolean = {
    token.getPartOfSpeechLevel1 == "名詞" || token.getPartOfSpeechLevel1 == "動詞"
  }

  def containsSynonym(word: String, pos: String, target: String, tPos: String): Boolean = {
    pos == tPos &&
      (JAWJAW.findHypernyms(word, posNameToId(pos)).asScala ++
        JAWJAW.findHyponyms(word, posNameToId(pos)).asScala ++ Seq(word)).toSeq.contains(target)
  }

}

