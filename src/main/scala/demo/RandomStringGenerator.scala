package demo

object RandomStringGenerator {

  def getRandomCombinationsOfString(): Iterator[String] = {
    val aToV: String = "abcdefghijklmnopqrstuv"
    val queries = for {
      first <- aToV
      second <- aToV
      third <- aToV
    } yield s"$first$second$third"
    Iterator.continually(queries).flatten
  }

}
