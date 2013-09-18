package controllers

import java.net.ConnectException
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration.DurationInt
import parsers.Parsers
import play.api.Routes
import play.api.libs.concurrent.Promise.timeout
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.Json.prettyPrint
import play.api.libs.json.Json.toJson
import play.api.mvc.Action
import play.api.mvc.Controller
import scala.collection.mutable.ArrayBuffer
import scala.language.postfixOps
import models.Feed

object Application extends Controller {
	private lazy val TIMEOUT = 3 minutes

	def index = Action {
		val feeds = Feed.getFeedsFromDB
		val feedsHTML = views.html.feeds(feeds.toIndexedSeq)
		Ok(views.html.index(channelView, feedsHTML))
	}

	private def channelView = {
		val channelsNames = ArrayBuffer[(String, String)]()

		channelsNames += (("channel_napwr", "NaPWr"))
		channelsNames += (("channel_samorzad", "Samorząd"))
		channelsNames += (("channel_estudent", "eStudent"))
		channelsNames += (("channel_pwr", "PWr"))

		(1 to 12) foreach { i =>
			channelsNames += (("channel_ss_w" + i, "Samorząd W" + i))
			channelsNames += (("channel_pwr_w" + i, "Wydział W" + i))
		}

		views.html.channel(channelsNames)
	}

	def feedsHtml = Action {
		try {
			val feeds = getFeeds(0)
			Feed.insertFeedsIntoDB(feeds)
			Ok(views.html.feeds(feeds))
		}
		catch {
			case e: ConnectException => NotFound("PWr Services don't response.")
		}
	}

	def feeds = Action { implicit request =>
		val futureResult = Future { getFeedsJSON }
		val timeoutFuture = timeout("Oops", 2 seconds)
		Async {
			Future.firstCompletedOf(Seq(futureResult, timeoutFuture)).map {
				case result: JsValue => Ok(prettyPrint(result))
				case err: String => InternalServerError(err)
			}
		}
	}

	private def getFeedsJSON = Json.parse("{\"feeds\" : [{\"title\" : \"Title 1\",\"link\" : \"http ://www.link1.com/\",\"description\" : \"Description 1\",\"channel\" : \"1\",\"date\" : \"1234235434\",\"image\" : \"http://icons.iconarchive.com/icons/sirea/happy-tree-friends/256/Giggles-icon.png\"},{\"title\" : \"Title 2\",\"link\" : \"http ://www.link2.com/\",\"description\" : \"Description 2\",\"channel\" : \"1\",\"date\" : \"12354235234\",\"image\" : \"http://icons.iconarchive.com/icons/sirea/happy-tree-friends/256/Cub-icon.png\"},{\"title\" : \"Title 3\",\"link\" : \"http ://www.link3.com/\",\"description\" : \"Description 3\",\"channel\" : \"2\",\"date\" : \"1334235234\",\"image\" : \"http://icons.iconarchive.com/icons/sirea/happy-tree-friends/256/TheMole-icon.png\"},{\"title\" : \"Title 4\",\"link\" : \"http ://www.link4.com/\",\"description\" : \"Description 4\",\"channel\" : \"2\",\"date\" : \"1654235234\",\"image\" : \"http://icons.iconarchive.com/icons/sirea/happy-tree-friends/256/Petunie-icon.png\"},{\"title\" : \"Title 5\",\"link\" : \"http ://www.link5.com/\",\"description\" : \"Description 5\",\"channel\" : \"3\",\"date\" : \"12342357234\",\"image\" : \"http://icons.iconarchive.com/icons/sirea/happy-tree-friends/256/Nutty-icon.png\"},{\"title\" : \"Title 6\",\"link\" : \"http ://www.link6.com/\",\"description\" : \"Description 6\",\"channel\" : \"3\",\"date\" : \"1234435237\",\"image\" : \"http://www.sireasgallery.com/iconset/happytreefriends/Mime_256x256_32.png\"},{\"title\" : \"Title 7\",\"link\" : \"http ://www.link7.com/\",\"description\" : \"Description 7\",\"channel\" : \"4\",\"date\" : \"1236235254\",\"image\" : \"http://www.sireasgallery.com/iconset/happytreefriends/Splendid_256x256_32.png\"}]}")

	/**
	  * ONE FEED
	  */
	def getFeedsForChannel(channelID: Int, lastUpdateTime: Long) = Action {
		val jsonObjectFeeds = Map("feeds" -> getFeeds(channelID, lastUpdateTime))
		Ok(toJson(jsonObjectFeeds))
	}

	private def getFeeds(channelID: Int, lastUpdateTime: Long) =
		getParserById(channelID, lastUpdateTime) getAllFeeds

	private def getParserById(channelID: Int, lastUpdateTime: Long) =
		Parsers.getParserById(channelID, lastUpdateTime)

	/**
	  * ALL FEEDS
	  */
	def getAllFeeds(lastUpdateTime: Long) = Action {
		val jsonObjectFeeds = Map("feeds" -> getFeeds(lastUpdateTime))
		Ok(toJson(jsonObjectFeeds))
	}

	private def getFeeds(lastUpdateTime: Long) = {
		val futures = for (id <- 1 to 28) yield getFutureById(id, lastUpdateTime)

		Await.result(Future.sequence(futures), TIMEOUT).flatten
	}

	private def getFutureById(channelID: Int, lastUpdateTime: Long) =
		getParserById(channelID, lastUpdateTime) getAllFeedsFuture

	/**
	  * FEEDS BY ID AND TIME
	  */
	def getFeedsByIdAndTime(channelsIDs: String, lastUpdateTimes: String) = Action {
		val jsonObjectFeeds = Map("feeds" -> getFeeds(channelsIDs, lastUpdateTimes))
		Ok(toJson(jsonObjectFeeds))
	}

	def getFeeds(channelsIDs: String, lastUpdateTimes: String) = {
		val channelsIdArray = channelsIDs split "\\&" toList
		val channelsUpdateTimesIdArray = lastUpdateTimes split "\\&" toList

		val futures = (channelsIdArray, channelsUpdateTimesIdArray).zipped.map((channelID, updateTime) => getFutureById(channelID.toInt, updateTime.toLong))

		Await.result(Future.sequence(futures), TIMEOUT).flatten
	}

	/**
	  * Making routes available from JavaScript
	  */

	def javascriptRoutes = Action { implicit request =>
		Ok(Routes.javascriptRouter("jsRoutes")(routes.javascript.Application.feedsHtml)).as(JAVASCRIPT)
	}
}