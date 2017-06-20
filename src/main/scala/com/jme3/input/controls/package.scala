package com.jme3.input

/**
  * Created by Brandon Barker on 6/19/17.
  */
package object controls {

  implicit class ActionListenerWrap(val uval: ActionListener) extends AnyVal {
    def onAction(action: Action, keyPressed: Boolean, tpf: Float): Unit =
      uval.onAction(action.name, keyPressed, tpf)


  }

}
