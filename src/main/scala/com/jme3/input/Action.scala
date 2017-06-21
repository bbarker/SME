package com.jme3.input

/**
  * Created by brandon on 6/19/17.
  */
//TODO: turn into trait parameters in Dotty?
abstract class Action(val name: String, val procedure: () => Unit) {}
