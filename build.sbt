name := "PWrRssReaderWeb"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  filters,
  cache
)

play.Project.playScalaSettings