package edu.knoldus.application

import edu.knoldus.operations.TwitterService
import org.apache.log4j.Logger
import twitter4j.{Query, Status, Twitter, TwitterFactory}


import scala.concurrent.ExecutionContext.Implicits.global

object ApplicationObject {
  def main(args: Array[String]): Unit = {
    val logger = Logger.getLogger(this.getClass)
    val obj = TwitterService("#shubham")
    val re = obj.numberOfTweets
    val as = obj.averageTweetsPerDay
    val alandrt = obj.averageLikesAndReTweets
    Thread.sleep(15000)
    as.map(x => logger.info(s"\n\n$x\n\n"))
    re.map(x => logger.info(s"\n\n$x\n\n"))
    re.map(x => logger.info(s"\n\n$x\n\n"))
    alandrt.map(x => logger.info(s"\n\n$x\n\n"))
  }
}