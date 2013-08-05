package parsers

import scala.xml.Node
import ChannelIDs._

class NaPWrParser(lastUpdateTime: Long)
		extends GeneralParser(ID_NaPWr, "http://www.napwr.pl/rss/najnowsze/", lastUpdateTime) {
	override protected def getDateFormat: String = "yyyy-MM-dd'T'HH:mm:ss"
	override protected def getImage(entry: Node): String = entry \\ "plakatMiniatura" text
}