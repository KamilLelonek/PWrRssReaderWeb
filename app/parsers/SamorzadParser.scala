package parsers

import ChannelIDs._

class SamorzadParser(lastUpdateTime: Long)
		extends GeneralParser(ID_Samorzad, "http://samorzad.pwr.wroc.pl/feed/", lastUpdateTime) {
	override protected def getDateFormat: String = "EEE, d MMM yyyy HH:mm:ss Z"
}