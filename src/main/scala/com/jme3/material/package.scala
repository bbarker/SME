package com.jme3

import com.jme3.asset.AssetManager

package object material {

  object Material {
    def apply(contentMan: AssetManager, defName: String): Material = 
      new Material(contentMan, defName)
  }
}