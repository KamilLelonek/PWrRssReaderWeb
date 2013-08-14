import sbt._
import play.Project._
import com.github.play2war.plugin._

object ApplicationBuild extends Build {

  val appName         = "PWrRssReaderWeb"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    anorm,
	"com.github.play2war.ext" %% "redirect-playlogger" % "1.0.1"
  )


  val main = play.Project(appName, appVersion, appDependencies)
	.settings(Play2WarPlugin.play2WarSettings: _*)
	.settings(Play2WarKeys.servletVersion := "3.0")
}