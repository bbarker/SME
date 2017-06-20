package com.jme3

/**
  * Created by brandon on 6/19/17.
  */
package object scene {

  implicit class SpatialWrap(val uval: Spatial) extends AnyVal {

    def toNode: Node = uval match {
      case ul: Node => ul
      case ul => throw new ClassCastException(s"Couldn't convert ${ul.getName} to Node")
    }

  }

}
