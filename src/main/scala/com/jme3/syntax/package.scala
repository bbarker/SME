package com.jme3

/**
  * Created by Brandon Barker on 6/21/17.
  */
package object syntax {

  @specialized def discard[A](evaluateForSideEffectOnly: A): Unit = {
    val _: A = evaluateForSideEffectOnly
    () //Return unit to prevent warning due to discarding value
  }

}
