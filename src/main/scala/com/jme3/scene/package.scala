package com.jme3

import com.jme3.scene.control.Control

/**
  * Created by Brandon Barker on 6/19/17.
  */
package object scene {

  implicit class SpatialWrap(val uval: Spatial) extends AnyVal {

    def toNode: Either[ClassCastException, Node] = uval match {
      case ul: Node => Right(ul)
      case ul => Left(new ClassCastException(s"Couldn't convert ${ul.getName} to Node"))
    }

  }

  object Node{

    /**
     * Constructor instantiates a new <code>Node</code> with a default empty
     * list for containing children.
     *
     * @param name the name of the scene element. This is required for
     * identification and comparison purposes.
     */
    def apply(name: String): Node = new Node(name)
  }

  implicit class NodeWrap(val uval: Node) extends AnyVal {
  
    def getControlMaybe[T <: Control](controlType: Class[T]): Option[T] = 
      Option(uval.getControl(controlType))

  }


}
