package com.jme3

/**
  * Created by brandon on 6/19/17.
  */
package object scene {

  implicit class SpatialWrap(val uval: Spatial) extends AnyVal {

    def toNode: Either[ClassCastException, Node] = uval match {
      case ul: Node => Right(ul)
      case ul => Left(new ClassCastException(s"Couldn't convert ${ul.getName} to Node"))
    }

  }

}
