package pl.pw.api.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pw.api.entity.User;
import pl.pw.api.entity.QUser;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

  public User save(User request) {
    return delegate.save(request);
  }

  public Optional<User> findOneByEmail(String email) {
    JPAQuery<User> q = new JPAQueryFactory(entityManager)
            .selectFrom(QUser.user);

    return Optional.ofNullable(q.where(QUser.user.email.eq(email)).fetchFirst());
  }

  public List<User> findAllByType(String type) {
    JPAQuery<User> q = new JPAQueryFactory(entityManager)
            .selectFrom(QUser.user);

    return q.where(QUser.user.accountType.eq(type)).fetch();
  }

  private interface JpaUser extends JpaRepository<User, String> {
  }

  private final JpaUser delegate;

  @PersistenceContext
  private final EntityManager entityManager;
}
