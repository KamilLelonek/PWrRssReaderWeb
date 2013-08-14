package parsers

import ChannelIDs._

class EStudentParser(lastUpdateTime: Long)
		extends GeneralParser(ID_eStudent, "http://estudent.pwr.wroc.pl/rss.php", lastUpdateTime) {
	override protected def getDateFormat: String = "yyyy-MM-dd HH:mm:ss"
}