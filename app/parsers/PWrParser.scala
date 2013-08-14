package parsers

import ChannelIDs._

class PWrParser(lastUpdateTime: Long)
		extends GeneralParser(ID_PWr, "http://www.portal.pwr.wroc.pl/rss,241.dhtml", lastUpdateTime) {
	override protected def getDateFormat: String = "EEE, d MMM yyyy HH:mm:ss Z"
}