package com.jme3.scene

package object shape {

  object Box {

  /**
    * Creates a new box.
    * <p>
    * The box has a center of 0,0,0 and extends in the out from the center by
    * the given amount in <em>each</em> direction. So, for example, a box
    * with extent of 0.5 would be the unit cube.
    *
    * @param xs the size of the box along the x axis, in both directions.
    * @param ys the size of the box along the y axis, in both directions.
    * @param zs the size of the box along the z axis, in both directions.
    */
    def apply(xs: Float, ys: Float, zs: Float): Box = new Box(xs, ys, zs)
  }
}