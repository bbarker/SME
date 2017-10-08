package tutorial

import com.jme3.input.Action

/**
  * Created by Brandon Barker on 6/19/17.
  */
object Actions {

  //Used in HelloInput, HelloCollision
  val Left:   Action = Action("Left")
  val Pause:  Action = Action("Pause")
  val Right:  Action = Action("Right")
  val Rotate: Action = Action("Rotate")
  val Up:     Action = Action("Up")
  val Down:   Action = Action("Down")
  val Jump:   Action = Action("Jump")

  //Used in HelloAnimation
  val Stand: Action = Action("stand")
  val Walk: Action = Action("Walk")

  //Used in HelloPicking, HelloAudio, HelloPhysics
  val Shoot: Action = Action("Shoot")

}
