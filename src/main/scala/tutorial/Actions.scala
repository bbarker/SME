package tutorial

/**
  * Created by Brandon Barker on 6/19/17.
  */
object Actions {

  implicit def actionToString(action: Action): String =
    action.getClass.getName.stripSuffix("$").replaceAll(".*\\$", "")

  object Action{
    def apply(name: String): Action = {
      Class.forName("tutorial.HelloInput$Helpers$" + name).newInstance()
        .asInstanceOf[Action]
    }
  }
  sealed abstract class Action {
    override def toString: String = actionToString(this)
  }
  final case class Rotate() extends Action
  final case class Right() extends Action
  final case class Left() extends Action
  final case class Pause() extends Action

}
