package com.jme3

/**
  * Created by Brandon Barker on 6/21/17.
  */
package object syntax {

  def discard(evaluateForSideEffectOnly: => Any): Unit = {
    evaluateForSideEffectOnly
    () //Return unit to prevent warning due to discarding value
  }

}
