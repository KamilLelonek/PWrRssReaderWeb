package models

import play.api.libs.json.Json

object Feed {
	implicit lazy val hogeFormat = Json.format[Feed]
}

case class Feed(
		title: String,
		link: String,
		description: String,
		channel: Int,
		date: Long,
		image: String) {

	override def toString =
		"title: " + title +
			"\nlink: " + link +
			"\ndescription: " + description +
			"\nchannel: " + channel +
			"\ndate: " + date +
			"\nimage: " + image
}