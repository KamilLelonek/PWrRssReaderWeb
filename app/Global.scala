import play.api._
import play.api.mvc._
import play.api.mvc.Results._

object Global extends GlobalSettings {
	override def onStart(app: Application) {
		Logger.info("Application has started")
	}

	override def onStop(app: Application) {
		Logger.info("Application shutdown")
	}

	override def onError(request: RequestHeader, ex: Throwable) = InternalServerError("{}")
	override def onBadRequest(request: RequestHeader, error: String) = BadRequest("{}")
	override def onHandlerNotFound(request: RequestHeader): Result = NotFound("{}")
}