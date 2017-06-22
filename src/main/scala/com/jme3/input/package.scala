package com.jme3

import com.jme3.input.controls.{InputListener, Trigger}

/**
  * Created by Brandon Barker on 6/21/17.
  */
package object input {

  implicit class InputManagerWrap(val uval: InputManager) extends AnyVal {
    def addMapping(action: Action, triggers: Trigger*): Unit =
      uval.addMapping(action.name, triggers: _*)
    def addListener(listener: InputListener, actions: Action*): Unit =
      uval.addListener(listener, actions.map(act => act.name): _*)

  }

}
