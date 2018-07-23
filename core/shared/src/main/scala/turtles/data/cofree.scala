/* Copyright 2014–2018 SlamData Inc. and Greg Pfeil.
 * Licensed under the Apache License, Version 2.0.
 * See https://github.com/sellout/turtles#copyright for details.
 */

package turtles.data

import turtles._
import turtles.patterns.EnvT

import cats._
import cats.free._
import cats.implicits._

trait CofreeInstances {
  implicit def cofreeSteppable[F[_], A](implicit F: Functor[F])
      : Steppable.Aux[Cofree[F, A], EnvT[A, F, ?]] =
    Steppable.fromAlgebraIso(
      t => Cofree(t.ask, Later(t.lower)),
      t => EnvT((t.head, t.tail.value)))

  implicit def cofreeRecursive[F[_], A](implicit F: Functor[F])
      : Recursive.Aux[Cofree[F, A], EnvT[A, F, ?]] =
    Recursive.withNativeRecursion

  implicit def cofreeCorecursive[F[_], A](implicit F: Functor[F])
      : Corecursive.Aux[Cofree[F, A], EnvT[A, F, ?]] =
    Corecursive.withNativeRecursion

  implicit def cofreeEq[F[_]: Traverse](implicit F: Delay[Eq, F]):
      Delay[Eq, Cofree[F, ?]] =
    new Delay[Eq, Cofree[F, ?]] {
      def apply[A](eq: Eq[A]) = {
        implicit val envtʹ: Delay[Eq, EnvT[A, F, ?]] = EnvT.equal(eq, F)

        Corecursive.corecursiveEq[Cofree[F, A], EnvT[A, F, ?]]
      }
    }

  implicit def cofreeShow[F[_]: Functor](implicit F: Delay[Show, F]):
      Delay[Show, Cofree[F, ?]] =
    new Delay[Show, Cofree[F, ?]] {
      def apply[A](s: Show[A]) = {
        implicit val envtʹ: Delay[Show, EnvT[A, F, ?]] = EnvT.show(s, F)

        Recursive.show[Cofree[F, A], EnvT[A, F, ?]]
      }
    }
}

object cofree extends CofreeInstances