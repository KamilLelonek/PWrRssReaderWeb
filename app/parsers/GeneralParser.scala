package parsers

import java.text.SimpleDateFormat
import scala.math._
import java.util.Locale
import scala.xml.Node
import org.jsoup.Jsoup
import models.Feed
import scala.collection.mutable.ListBuffer
import scala.xml.Elem
import scala.util.control.Breaks._
import java.net.URL
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.xml.NodeSeq
import org.jsoup.safety.Whitelist
import org.jsoup.nodes.Entities.EscapeMode

abstract class GeneralParser(channelID: Int, link: String, lastUpdateTime: Long) {
	def getAllFeedsFuture = future { getAllFeeds }

	def getAllFeeds: ListBuffer[Feed] = {
		val xml = getXML(link)
		parseXML(xml, channelID)
	}

	protected def getDateFormat: String
	protected def getImage(entry: Node): String = findImageTag(getTextFromTag(entry, "description"))
	protected def getTextFromTag(entry: Node, tag: String) = entry \ tag text

	private def getXML(link: String) = {
		val url = new URL(link)
		val conn = url.openConnection
		scala.xml.XML.load(conn.getInputStream)
	}

	private def parseXML(xml: Elem, channelID: Int) = {
		val itemTag = xml \ "channel" \ "item"
		val itemNodesCount = itemTag.count(_ => true)
		val loopCount = min(itemNodesCount, 20)
		getFeedsArray(itemTag, loopCount)
	}

	private def getFeedsArray(itemTag: NodeSeq, loopCount: Int) = {
		val result = new ListBuffer[Feed]
		breakable {
			itemTag.iterator.take(loopCount) foreach {
				tag =>
					val feed = parseTag(tag)
					if (feed.date <= lastUpdateTime) break
					result += feed
			}
		}
		result
	}

	private def parseTag(entry: Node) = {
		val title = getTextFromTag(entry, "title")
		val link = getTextFromTag(entry, "link")
		val description = getCleanTextFromTag(entry, "description")
		val date = parseDate(entry)
		val image = getImage(entry)

		new Feed(title, link, description, channelID, date, image)
	}

	private def getCleanTextFromTag(entry: Node, tag: String) = {
		val tagText = getTextFromTag(entry, tag)
		val html = Jsoup.clean(tagText, Whitelist.basic)
    val doc = Jsoup.parse(html, "utf-8")
    doc.outputSettings.escapeMode(EscapeMode.xhtml)
    doc.body.html
	}

	private def parseDate(entry: Node) = {
		val dateString = entry \ "pubDate" text
		val dateFormat = getDateFormat
		val dateFormatter = new SimpleDateFormat(dateFormat, Locale.ENGLISH)
		val date = dateFormatter.parse(dateString)
		date.getTime
	}

	protected def findImageTag(description: String) = {
		val document = Jsoup.parse(description)
		document.select("img[src]").attr("src")
	}
}