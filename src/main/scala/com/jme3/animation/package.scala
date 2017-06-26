package com.jme3

import com.jme3.input.Action

package object animation {

  implicit class AnimChannelWrap(val uval: AnimChannel) extends AnyVal {

    /**
     * Set the current animation that is played by this AnimChannel.
     * <p>
     * See {@link #setAnim(java.lang.String, float)}.
     * The blendTime argument by default is 150 milliseconds.
     * 
     * @param action The action (name) of the animation to play
     */
     def setAnim(action: Action): Unit = uval.setAnim(action.name)

   /**
    * Set the current animation that is played by this AnimChannel.
    * <p>
    * This resets the time to zero, and optionally blends the animation
    * over <code>blendTime</code> seconds with the currently playing animation.
    * Notice that this method will reset the control's speed to 1.0.
    *
    * @param action The action (name) of the animation to play
    * @param blendTime The blend time over which to blend the new animation
    * with the old one. If zero, then no blending will occur and the new
    * animation will be applied instantly.
    */
    def setAnim(action: Action, blendTime: Float): Unit = uval.setAnim(action.name, blendTime)


  }

  implicit class AnimEventListenerWrap(val uval: AnimEventListener) extends AnyVal {
    
    /**
     * Invoked when an animation "cycle" is done. For non-looping animations,
     * this event is invoked when the animation is finished playing. For
     * looping animations, this even is invoked each time the animation is restarted.
     *
     * @param control The control to which the listener is assigned.
     * @param channel The channel being altered
     * @param action The new animation action that is done.
     */
    def onAnimCycleDone(control: AnimControl, channel: AnimChannel, action: Action): Unit = 
      uval.onAnimCycleDone(control, channel, action.name)

    /**
     * Invoked when a animation is set to play by the user on the given channel.
     *
     * @param control The control to which the listener is assigned.
     * @param channel The channel being altered
     * @param action The new animation action set.
     */
     def onAnimChange(control: AnimControl, channel: AnimChannel, action: Action): Unit =
       uval.onAnimChange(control, channel, action.name)  
  }


}