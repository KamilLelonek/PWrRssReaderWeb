import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "PWrRssReaderWeb"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
	"com.newrelic.agent.java" % "newrelic-agent" % "2.x.x",
    jdbc,
    anorm
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )
}