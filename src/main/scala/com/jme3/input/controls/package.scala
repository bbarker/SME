package com.jme3.input

/**
  * Created by Brandon Barker on 6/19/17.
  */
package object controls {

  implicit class ActionListenerWrap(val uval: ActionListener) extends AnyVal {

    /**
     * Called when an input to which this listener is registered to is invoked.
     * 
     * @param action The action (name) of the mapping that was invoked
     * @param isPressed True if the action is "pressed", false otherwise
     * @param tpf The time per frame value.
     */
    def onAction(action: Action, keyPressed: Boolean, tpf: Float): Unit =
      uval.onAction(action.name, keyPressed, tpf)
  }

  implicit class AnalogListenerWrap(val uval: AnalogListener) extends AnyVal {

    /**
     * Called to notify the implementation that an analog event has occurred.
     *
     * The results of KeyTrigger and MouseButtonTrigger events will have tpf
     *  == value.
     *
     * @param action The action (name) of the mapping that was invoked
     * @param value Value of the axis, from 0 to 1.
     * @param tpf The time per frame value.
     */
    def onAnalog(action: Action, value: Float, tpf: Float): Unit =
      uval.onAnalog(action.name, value, tpf)
  }

}
