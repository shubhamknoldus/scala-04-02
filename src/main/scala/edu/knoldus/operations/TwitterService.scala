package edu.knoldus.operations

import com.typesafe.config.{Config, ConfigFactory}
import twitter4j.auth.AccessToken
import twitter4j.{Query, Twitter, TwitterFactory}

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class TwitterService(hashTag: String) {
  val twitter: Twitter = new TwitterFactory().getInstance()
  val loaded: Config = ConfigFactory.load()
  val consumerKey: String = loaded.getString("twitterKeys.Consumer.key")
  val consumerSecret: String = loaded.getString("twitterKeys.Consumer.secret")
  val accessToken: String = loaded.getString("twitterKeys.accessToken.token")
  val accessSecret: String = loaded.getString("twitterKeys.accessToken.secret")
  twitter.setOAuthConsumer(consumerKey, consumerSecret)
  twitter.setOAuthAccessToken(new AccessToken(accessToken, accessSecret))
  val query = new Query(hashTag)
  query.setCount(100)
  query.setSince("2017-12-01")
  query.until("2018-02-04")

  /**
    * @return number of tweets for the class param
    */
  def numberOfTweets: Future[Int] = Future {
    val result = twitter.search(query) /*.since("2018-01-24")*/
    result.getTweets.size
  }

  /**
    * @return a string formatted with average number of
    *         likes and average number of reTweets
    */
  def averageLikesAndReTweets: Future[String] = Future {
    val statusList = twitter.search(query).getTweets.asScala.toList
    val avgLikes = statusList.foldLeft(0)((x, y) => x + y.getFavoriteCount) / statusList.size
    val avgReTweets = statusList.foldLeft(0)((x, y) => x + y.getRetweetCount) / statusList.size
    s"\n\n\naverage number of likes are $avgLikes and average number of reTweets are $avgReTweets\n\n\n"
  }

  /**
    * @return average number of tweets per day
    */
  def averageTweetsPerDay: Future[Double] = Future {
    val result = twitter.search(query)
    val scalaList = result.getTweets.asScala.toList
    scalaList.map(x => x.getCreatedAt).length / 10
  }
}

