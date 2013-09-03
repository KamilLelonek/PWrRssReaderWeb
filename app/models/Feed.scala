package models

import play.api.libs.json.Json
import java.util.Date
import java.text.SimpleDateFormat
import Feed._
import java.util.Locale

object Feed {
	implicit lazy val hogeFormat = Json.format[Feed]
	lazy val dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss", new Locale("pl", "PL"))
}

case class Feed(
		title: String,
		link: String,
		description: String,
		channel: Int,
		date: Long,
		image: String) {

	def readableDate = dateFormat.format(new Date(date))

	def readableDescription = {}

	override def toString =
		"title: " + title +
			"\nlink: " + link +
			"\ndescription: " + description +
			"\nchannel: " + channel +
			"\ndate: " + date +
			"\nimage: " + image
}