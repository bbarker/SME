package com.jme3.scene

import com.jme3.animation.Skeleton

package object debug {

  object SkeletonDebugger {

  /**
    * Creates a debugger with no length data. The wires will be a connection between the bones' heads only.
    * The points will show the bones' heads only and no dotted line of inter bones connection will be visible.
    * @param name
    *            the name of the debugger's node
    * @param skeleton
    *            the skeleton that will be shown
    */
    def apply(name: String, skeleton: Skeleton) = new SkeletonDebugger(name, skeleton)
  }
}