package tutorial

import com.jme3.input.Action

/**
  * Created by Brandon Barker on 6/19/17.
  */
object Actions {

  implicit def actionToString(action: AppAction): String =
    action.getClass.getName.stripSuffix("$").replaceAll(".*\\$", "")

  object AppAction{
    def apply(name: String): AppAction = {
      Class.forName("tutorial.HelloInput$Helpers$" + name).newInstance()
        .asInstanceOf[AppAction]
    }
  }
  sealed abstract class AppAction(name: String, procedure: () => Unit)
  extends Action(name, procedure) {
    override def toString: String = actionToString(this)
  }
  final object Rotate {
    val name = "Rotate"
    def procedure = ()
  }
  final case class Right() extends AppAction
  final case class Left() extends AppAction
  final case class Pause() extends AppAction

}
