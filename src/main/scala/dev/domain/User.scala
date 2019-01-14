package dev.domain

import javax.persistence.{Entity, Id}

@Entity
class User (xy: Int) {
  @Id
  var x: Int = xy
  var y: Int = 0

}
