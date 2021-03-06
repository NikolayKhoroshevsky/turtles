/* Copyright 2014–2018 SlamData Inc. and Greg Pfeil.
 * Licensed under the Apache License, Version 2.0.
 * See https://github.com/sellout/turtles#copyright for details.
 */

package turtles.runners

import turtles._

import org.scalatest._

abstract class BirecRunner[F[_]] {
  // NB: This is defined as a function to make the many definition sites
  //     slightly shorter.
  def run[T](implicit T: Birecursive.Aux[T, F]): T => Assertion
}
