package models

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import scala.collection.immutable.IndexedSeq
import scala.language.postfixOps
import org.jsoup.Jsoup
import Feed._
import parsers.ChannelIDs._
import play.api.libs.json.Json
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

object Feed {
	implicit lazy val hogeFormat = Json.format[Feed]

	private lazy val dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss", new Locale("pl", "PL"))
	private lazy val MAX_LENGTH = 200
	private lazy val ELLIPSIS = "..."
	private lazy val UNKNOWN = "?"

	def getSiteForChannel(channelID: Long) = channelID match {
		case ID_NaPWr => "http://www.napwr.pl/"
		case ID_Samorzad => "http://samorzad.pwr.wroc.pl/"
		case ID_eStudent => "http://www.estudent.pwr.wroc.pl/"
		case ID_PWr => "http://www.portal.pwr.wroc.pl/"
		case ID_PWr_W1 => "http://www.wa.pwr.wroc.pl/"
		case ID_SS_W1 => "http://wa.samorzad.pwr.wroc.pl/"
		case ID_PWr_W2 => "http://www.wbliw.pwr.wroc.pl/l"
		case ID_SS_W2 => "http://wbliw.samorzad.pwr.wroc.pl/"
		case ID_PWr_W3 => "http://www.wch.pwr.wroc.pl/"
		case ID_SS_W3 => "http://wch.samorzad.pwr.wroc.pl/"
		case ID_PWr_W4 => "http://www.weka.pwr.wroc.pl/"
		case ID_SS_W4 => "http://weka.samorzad.pwr.wroc.pl/"
		case ID_PWr_W5 => "http://www.weny.pwr.wroc.pl/"
		case ID_SS_W5 => "http://weny.samorzad.pwr.wroc.pl/"
		case ID_PWr_W6 => "http://www.wggg.pwr.wroc.pl/"
		case ID_SS_W6 => "http://wggg.samorzad.pwr.wroc.pl/"
		case ID_PWr_W7 => "http://www.wis.pwr.wroc.pl/"
		case ID_SS_W7 => "http://wis.samorzad.pwr.wroc.pl/"
		case ID_PWr_W8 => "http://www.wiz.pwr.wroc.pl/"
		case ID_SS_W8 => "http://wiz.samorzad.pwr.wroc.pl/"
		case ID_PWr_W9 => "http://www.wme.pwr.wroc.pl/"
		case ID_SS_W9 => "http://wme.samorzad.pwr.wroc.pl/"
		case ID_PWr_W10 => "http://www.wm.pwr.wroc.pl/"
		case ID_SS_W10 => "http://wm.samorzad.pwr.wroc.pl/"
		case ID_PWr_W11 => "http://www.wppt.pwr.wroc.pl/"
		case ID_SS_W11 => "http://wppt.samorzad.pwr.wroc.pl/"
		case ID_PWr_W12 => "http://www.wemif.pwr.wroc.pl/"
		case ID_SS_W12 => "http://wemif.samorzad.pwr.wroc.pl/"
	}

	def getChannelName(channelID: Long) = channelID match {
		case ID_NaPWr => "channel_napwr"
		case ID_Samorzad => "channel_samorzad"
		case ID_eStudent => "channel_estudent"
		case ID_PWr => "channel_pwr"
		case ID_PWr_W1 => "channel_pwr_w1"
		case ID_SS_W1 => "channel_ss_w1"
		case ID_PWr_W2 => "channel_pwr_w2"
		case ID_SS_W2 => "channel_ss_w2"
		case ID_PWr_W3 => "channel_pwr_w3"
		case ID_SS_W3 => "channel_ss_w3"
		case ID_PWr_W4 => "channel_pwr_w4"
		case ID_SS_W4 => "channel_ss_w4"
		case ID_PWr_W5 => "channel_pwr_w5"
		case ID_SS_W5 => "channel_ss_w5"
		case ID_PWr_W6 => "channel_pwr_w6"
		case ID_SS_W6 => "channel_ss_w6"
		case ID_PWr_W7 => "channel_pwr_w7"
		case ID_SS_W7 => "channel_ss_w7"
		case ID_PWr_W8 => "channel_pwr_w8"
		case ID_SS_W8 => "channel_ss_w8"
		case ID_PWr_W9 => "channel_pwr_w9"
		case ID_SS_W9 => "channel_ss_w9"
		case ID_PWr_W10 => "channel_pwr_w10"
		case ID_SS_W10 => "channel_ss_w10"
		case ID_PWr_W11 => "channel_pwr_w11"
		case ID_SS_W11 => "channel_ss_w11"
		case ID_PWr_W12 => "channel_pwr_w12"
		case ID_SS_W12 => "channel_ss_w12"
	}

	def insertFeedsIntoDB(feeds: IndexedSeq[Feed]) = feeds foreach { feed => insertFeedIntoDB(feed) }

	private def insertFeedIntoDB(feed: Feed) = {
		DB.withConnection {
			implicit connection =>
				SQL(
					"""
	          insert into feeds values (
	            {title}, {link}, {description}, {channel}, {date}, {image}
	          )
	        """).on(
						'title -> feed.title,
						'link -> feed.link,
						'description -> feed.description,
						'channel -> feed.channel,
						'date -> feed.date,
						'image -> feed.image).executeUpdate()
		}
	}

	def getFeedsFromDB = {
		DB.withConnection {
			implicit connection =>
				SQL("select * from feeds").as(feedORM *)
		}
	}

	val feedORM = {
		get[String]("title") ~
			get[String]("link") ~
			get[String]("description") ~
			get[Long]("channel") ~
			get[Long]("date") ~
			get[String]("image") map {
				case title ~ link ~ description ~ channel ~ date ~ image =>
					Feed(title, link, description, channel, date, image)
			}
	}
}

case class Feed(
		title: String,
		link: String,
		description: String,
		channel: Long,
		date: Long,
		image: String) {

	def readableTitle = if (isEmpty(title)) UNKNOWN else title

	def readableDate = {
		val dateString = dateFormat.format(new Date(date))
		if (isEmpty(dateString)) UNKNOWN else dateString
	}

	def readableDescription = {
		val text = Jsoup.parse(description).text().trim

		if (text.length > MAX_LENGTH)
			text.substring(0, MAX_LENGTH) + ELLIPSIS
		else if (isEmpty(text)) ELLIPSIS else text
	}

	def channelImage = "/assets/images/" + getChannelName(channel) + ".png"
	def channelSite = getSiteForChannel(channel)
	def channelName = getChannelName(channel)

	private def isEmpty(string: String) =
		string == null ||
			string.trim.length < 3

	override def toString =
		"title: " + title +
			"\nlink: " + link +
			"\ndescription: " + description +
			"\nchannel: " + channel +
			"\ndate: " + date +
			"\nimage: " + image
}