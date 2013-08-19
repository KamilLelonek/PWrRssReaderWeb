package parsers

import parsers.ChannelIDs._
import scala.xml.Node

object Parsers {
  def getParserById(id: Int, lastUpdateTime: Long): GeneralParser =
    id match {
      case ChannelIDs.ID_NaPWr => new NaPWrParser(lastUpdateTime)
      case ChannelIDs.ID_Samorzad => new SamorzadParser(lastUpdateTime)
      case ChannelIDs.ID_eStudent => new EStudentParser(lastUpdateTime)
      case ChannelIDs.ID_PWr => new PWrParser(lastUpdateTime)
      case ChannelIDs.ID_PWr_W1 => new PWrW1Parser(lastUpdateTime)
      case ChannelIDs.ID_SS_W1 => new PWrSSW1Parser(lastUpdateTime)
      case ChannelIDs.ID_PWr_W2 => new PWrW2Parser(lastUpdateTime)
      case ChannelIDs.ID_SS_W2 => new PWrSSW2Parser(lastUpdateTime)
      case ChannelIDs.ID_PWr_W3 => new PWrW3Parser(lastUpdateTime)
      case ChannelIDs.ID_SS_W3 => new PWrSSW3Parser(lastUpdateTime)
      case ChannelIDs.ID_PWr_W4 => new PWrW4Parser(lastUpdateTime)
      case ChannelIDs.ID_SS_W4 => new PWrSSW4Parser(lastUpdateTime)
      case ChannelIDs.ID_PWr_W5 => new PWrW5Parser(lastUpdateTime)
      case ChannelIDs.ID_SS_W5 => new PWrSSW5Parser(lastUpdateTime)
      case ChannelIDs.ID_PWr_W6 => new PWrW6Parser(lastUpdateTime)
      case ChannelIDs.ID_SS_W6 => new PWrSSW6Parser(lastUpdateTime)
      case ChannelIDs.ID_PWr_W7 => new PWrW7Parser(lastUpdateTime)
      case ChannelIDs.ID_SS_W7 => new PWrSSW7Parser(lastUpdateTime)
      case ChannelIDs.ID_PWr_W8 => new PWrW8Parser(lastUpdateTime)
      case ChannelIDs.ID_SS_W8 => new PWrSSW8Parser(lastUpdateTime)
      case ChannelIDs.ID_PWr_W9 => new PWrW9Parser(lastUpdateTime)
      case ChannelIDs.ID_SS_W9 => new PWrSSW9Parser(lastUpdateTime)
      case ChannelIDs.ID_PWr_W10 => new PWrW10Parser(lastUpdateTime)
      case ChannelIDs.ID_SS_W10 => new PWrSSW10Parser(lastUpdateTime)
      case ChannelIDs.ID_PWr_W11 => new PWrW11Parser(lastUpdateTime)
      case ChannelIDs.ID_SS_W11 => new PWrSSW11Parser(lastUpdateTime)
      case ChannelIDs.ID_PWr_W12 => new PWrW12Parser(lastUpdateTime)
      case ChannelIDs.ID_SS_W12 => new PWrSSW12Parser(lastUpdateTime)
      case _ => null
    }

  class SamorzadParser(lastUpdateTime: Long)
    extends GeneralParser(ID_Samorzad, "http://samorzad.pwr.wroc.pl/feed/", lastUpdateTime) {
  }

  class EStudentParser(lastUpdateTime: Long)
    extends GeneralParser(ID_eStudent, "http://estudent.pwr.wroc.pl/rss.php", lastUpdateTime) {
    override protected def getDateFormat: String = "yyyy-MM-dd HH:mm:ss"
  }

  class NaPWrParser(lastUpdateTime: Long)
    extends GeneralParser(ID_NaPWr, "http://www.napwr.pl/rss/najnowsze/", lastUpdateTime) {
    override protected def getDateFormat: String = "yyyy-MM-dd'T'HH:mm:ss"

    override protected def getImage(entry: Node): String = entry \\ "plakatMiniatura" text
  }

  class PWrParser(lastUpdateTime: Long)
    extends GeneralParser(ID_PWr, "http://www.portal.pwr.wroc.pl/rss,241.dhtml", lastUpdateTime) {
  }

  //	Architektura
  class PWrW1Parser(lastUpdateTime: Long)
    extends GeneralParser(ID_PWr_W1, "http://www.wa.pwr.wroc.pl/rss,21.xml", lastUpdateTime)

  class PWrSSW1Parser(lastUpdateTime: Long)
    extends GeneralParser(ID_SS_W1, "http://wa.samorzad.pwr.wroc.pl/feed/", lastUpdateTime)

  //	Budownictwo
  class PWrW2Parser(lastUpdateTime: Long)
    extends GeneralParser(ID_PWr_W2, "http://www.wbliw.pwr.wroc.pl/rss,31.xml", lastUpdateTime)

  class PWrSSW2Parser(lastUpdateTime: Long)
    extends GeneralParser(ID_SS_W2, "http://wbliw.samorzad.pwr.wroc.pl/feed/", lastUpdateTime)

  //	Chemia
  class PWrW3Parser(lastUpdateTime: Long)
    extends GeneralParser(ID_PWr_W3, "http://www.wch.pwr.wroc.pl/rss,11.xml", lastUpdateTime)

  class PWrSSW3Parser(lastUpdateTime: Long)
    extends GeneralParser(ID_SS_W3, "http://wch.samorzad.pwr.wroc.pl/feed/", lastUpdateTime)

  //	Elektronika
  class PWrW4Parser(lastUpdateTime: Long)
    extends GeneralParser(ID_PWr_W4, "http://www.weka.pwr.wroc.pl/rss,41.xml", lastUpdateTime)

  class PWrSSW4Parser(lastUpdateTime: Long)
    extends GeneralParser(ID_SS_W4, "http://weka.samorzad.pwr.wroc.pl/feed/", lastUpdateTime)

  //	Elektryka
  class PWrW5Parser(lastUpdateTime: Long)
    extends GeneralParser(ID_PWr_W5, "http://www.weny.pwr.wroc.pl/rss,51.xml", lastUpdateTime)

  class PWrSSW5Parser(lastUpdateTime: Long)
    extends GeneralParser(ID_SS_W5, "http://weny.samorzad.pwr.wroc.pl/feed/", lastUpdateTime)

  //	Geoinżynieria, Górnictwo i Geologia
  class PWrW6Parser(lastUpdateTime: Long)
    extends GeneralParser(ID_PWr_W6, "http://www.wggg.pwr.wroc.pl/rss,61.xml", lastUpdateTime)

  class PWrSSW6Parser(lastUpdateTime: Long)
    extends GeneralParser(ID_SS_W6, "http://wggg.samorzad.pwr.wroc.pl/feed/", lastUpdateTime)

  //	Inżynieria Środowiska
  class PWrW7Parser(lastUpdateTime: Long)
    extends GeneralParser(ID_PWr_W7, "http://www.wis.pwr.wroc.pl/rss,71.xml", lastUpdateTime)

  class PWrSSW7Parser(lastUpdateTime: Long)
    extends GeneralParser(ID_SS_W7, "http://wis.samorzad.pwr.wroc.pl/feed/", lastUpdateTime)

  //	Informatyka i Zarządzanie
  class PWrW8Parser(lastUpdateTime: Long)
    extends GeneralParser(ID_PWr_W8, "http://www.wiz.pwr.wroc.pl/rss,1.xml", lastUpdateTime)

  class PWrSSW8Parser(lastUpdateTime: Long)
    extends GeneralParser(ID_SS_W8, "http://wiz.samorzad.pwr.wroc.pl/feed/", lastUpdateTime)

  //	Mechaniczno-energetyczny
  class PWrW9Parser(lastUpdateTime: Long)
    extends GeneralParser(ID_PWr_W9, "http://www.wme.pwr.wroc.pl/rss,81.xml", lastUpdateTime)

  class PWrSSW9Parser(lastUpdateTime: Long)
    extends GeneralParser(ID_SS_W9, "http://wme.samorzad.pwr.wroc.pl/feed/", lastUpdateTime)

  //	Mechanika
  class PWrW10Parser(lastUpdateTime: Long)
    extends GeneralParser(ID_PWr_W10, "http://www.wm.pwr.wroc.pl/rss,91.xml", lastUpdateTime)

  class PWrSSW10Parser(lastUpdateTime: Long)
    extends GeneralParser(ID_SS_W10, "http://wm.samorzad.pwr.wroc.pl/feed/", lastUpdateTime)

  //	Podstawowe Problemy Techniki
  class PWrW11Parser(lastUpdateTime: Long)
    extends GeneralParser(ID_PWr_W11, "http://www.wppt.pwr.wroc.pl/rss,101.xml", lastUpdateTime)

  class PWrSSW11Parser(lastUpdateTime: Long)
    extends GeneralParser(ID_SS_W11, "http://wppt.samorzad.pwr.wroc.pl/feed/", lastUpdateTime)

  //	Mikrosystemy i Fotonika
  class PWrW12Parser(lastUpdateTime: Long)
    extends GeneralParser(ID_PWr_W12, "http://www.wemif.pwr.wroc.pl/rss,111.xml", lastUpdateTime)

  class PWrSSW12Parser(lastUpdateTime: Long)
    extends GeneralParser(ID_SS_W12, "http://wemif.samorzad.pwr.wroc.pl/feed/", lastUpdateTime)

}