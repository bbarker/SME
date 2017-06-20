package com.jme3

/**
  * Created by Brandon Barker on 6/19/17.
  */
package object app {

  implicit class SimpleApplicationWrap(val uval: SimpleApplication) extends AnyVal {

    //FIXME: proof of concept, remove later
    def testWrap: String = uval.hashCode().toString


  }

}
