package controllers

import scala.collection.mutable.ListBuffer
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration.DurationInt

import models.Feed
import parsers.EStudentParser
import parsers.NaPWrParser
import parsers.PWrParser
import parsers.SamorzadParser
import play.api.libs.json.Json
import play.api.libs.json.Json.prettyPrint
import play.api.libs.json.Json.toJson
import play.api.mvc.Action
import play.api.mvc.Controller

object Application extends Controller {

	def index = Action { Ok("{}") }

	def feeds = Action {
		val json = Json.parse("{\"feeds\" : [{\"title\" : \"Title 1\",\"link\" : \"http ://www.link1.com/\",\"description\" : \"Description 1\",\"channel\" : \"1\",\"date\" : \"1234235434\",\"image\" : \"http://icons.iconarchive.com/icons/sirea/happy-tree-friends/256/Giggles-icon.png\"},{\"title\" : \"Title 2\",\"link\" : \"http ://www.link2.com/\",\"description\" : \"Description 2\",\"channel\" : \"1\",\"date\" : \"12354235234\",\"image\" : \"http://icons.iconarchive.com/icons/sirea/happy-tree-friends/256/Cub-icon.png\"},{\"title\" : \"Title 3\",\"link\" : \"http ://www.link3.com/\",\"description\" : \"Description 3\",\"channel\" : \"2\",\"date\" : \"1334235234\",\"image\" : \"http://icons.iconarchive.com/icons/sirea/happy-tree-friends/256/TheMole-icon.png\"},{\"title\" : \"Title 4\",\"link\" : \"http ://www.link4.com/\",\"description\" : \"Description 4\",\"channel\" : \"2\",\"date\" : \"1654235234\",\"image\" : \"http://icons.iconarchive.com/icons/sirea/happy-tree-friends/256/Petunie-icon.png\"},{\"title\" : \"Title 5\",\"link\" : \"http ://www.link5.com/\",\"description\" : \"Description 5\",\"channel\" : \"3\",\"date\" : \"12342357234\",\"image\" : \"http://icons.iconarchive.com/icons/sirea/happy-tree-friends/256/Nutty-icon.png\"},{\"title\" : \"Title 6\",\"link\" : \"http ://www.link6.com/\",\"description\" : \"Description 6\",\"channel\" : \"3\",\"date\" : \"1234435237\",\"image\" : \"http://www.sireasgallery.com/iconset/happytreefriends/Mime_256x256_32.png\"},{\"title\" : \"Title 7\",\"link\" : \"http ://www.link7.com/\",\"description\" : \"Description 7\",\"channel\" : \"4\",\"date\" : \"1236235254\",\"image\" : \"http://www.sireasgallery.com/iconset/happytreefriends/Splendid_256x256_32.png\"}]}")
		Ok(prettyPrint(json))
	}

	def feedsFrom(lastUpdateTime: Long) = Action {
		val jsonObjectFeeds = Map("feeds" -> getFeeds(lastUpdateTime))
		Ok(toJson(jsonObjectFeeds))
	}

	def getFeeds(lastUpdateTime: Long) = {
		val futures = List(
			(new NaPWrParser(lastUpdateTime)).getAllFeedsFuture,
			(new SamorzadParser(lastUpdateTime)).getAllFeedsFuture,
			(new EStudentParser(lastUpdateTime)).getAllFeedsFuture,
			(new PWrParser(lastUpdateTime)).getAllFeedsFuture)

		Await.result(Future.sequence(futures), 1 minute).flatten
	}
}