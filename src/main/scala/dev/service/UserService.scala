package dev.service

import dev.domain.User
import javax.inject.{Inject, Named}
import org.hibernate.SessionFactory
import org.springframework.transaction.annotation.Transactional

@Named @Transactional class UserService @Inject()(sf : SessionFactory){
  def test = sf.getCurrentSession.persist( new User(1))
}