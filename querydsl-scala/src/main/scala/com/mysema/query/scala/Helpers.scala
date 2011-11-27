package com.mysema.query.scala

import scala.collection.JavaConversions._
import com.mysema.query._
import com.mysema.query.types._
import com.mysema.query.scala.GroupBy._

import Projections._
import TypeDefs._

object Helpers extends Helpers

trait Helpers extends Projections with GroupBy {
  
  implicit def toRichSimpleProjectable[T](p: SimpleProjectable[T]) = new RichSimpleProjectable(p) 
  
  implicit def toRichProjectable(p: Projectable) = new RichProjectable(p)  
}

class RichSimpleProjectable[T](private val p: SimpleProjectable[T]) {
  
  def select: List[T] = p.list.toList
  
  def single: Option[T] = Option(p.singleResult())
  
  def unique: Option[T] = Option(p.uniqueResult())
  
  override def toString: String = p.toString
  
}

class RichProjectable(private val p: Projectable) {
  
  def select[T](e: Ex[T]): List[T] = p.list(e).toList
  
  def select[T,U](e1: Ex[T], e2: Ex[U]): List[(T,U)] = p.list((e1,e2)).toList
  
  def select[T,U,V](e1: Ex[T], e2: Ex[U], e3: Ex[V]): List[(T,U,V)] = p.list((e1,e2,e3)).toList
  
  def select[T,U,V,W](e1: Ex[T], e2: Ex[U], e3: Ex[V], e4: Ex[W]): List[(T,U,V,W)] = p.list((e1,e2,e3,e4)).toList
  
  def select[T,U,V,W,X](e1: Ex[T], e2: Ex[U], e3: Ex[V], e4: Ex[W], e5: Ex[X]): List[(T,U,V,W,X)] = p.list((e1,e2,e3,e4,e5)).toList  
  
  // TODO : generalize this
  def selectGrouped[K,T,V](key: Ex[K], parent: Ex[T], child: Ex[V]): List[(T,Set[V])] = {
    p.transform(groupBy(key).as((parent, set(child)))).values.toList    
  }  
  
  def single[T](expr: Ex[T]): Option[T] = Option(p.singleResult(expr))
  
  def unique[T](expr: Ex[T]): Option[T] = Option(p.uniqueResult(expr))
  
  override def toString: String = p.toString
  
}


