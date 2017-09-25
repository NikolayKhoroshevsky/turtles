/*
 * Copyright 2014–2017 SlamData Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package turtles.patterns

import slamdata.Predef.{Eq => _, _}
import turtles._
import turtles.helpers._
import turtles.scalacheck.arbitrary._

import scala.Predef.{implicitly => imp}

import cats._
import cats.implicits._
import cats.laws.discipline._
import org.scalacheck._
import org.specs2.ScalaCheck
import org.specs2.mutable._
import org.typelevel.discipline.specs2.mutable._

class ListFSpec extends Specification with ScalaCheck with AlgebraChecks {
  "ListF" >> {
    // checkAll("ListF[String, Int]", EqTests[ListF[String, Int]].eqv)
    checkAll("ListF", BitraverseTests[ListF].bitraverse)

    checkAlgebraIsoLaws(
      "ListF ⇔ List", ListF.listIso[Int])(
      Arbitrary(Gen.listOf(Arbitrary.arbInt.arbitrary)), imp, imp, imp, imp)
  }
}