package pl.pw.api.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pw.api.entity.QVisit;
import pl.pw.api.entity.Visit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VisitRepository {

  public Optional<Visit> findOneById(String id) {
    JPAQuery<Visit> q = new JPAQueryFactory(entityManager)
            .selectFrom(QVisit.visit);

    return Optional.ofNullable(q.where(QVisit.visit.id.eq(id)).fetchFirst());
  }

  private interface JpaVisit extends JpaRepository<Visit, String>{};

  private final JpaVisit delegate;

  @PersistenceContext
  private final EntityManager entityManager;

  public Visit save(Visit request) {
    return delegate.save(request);
  }

  public List<Visit> findAllByEmail(String email) {
    JPAQuery<Visit> q = new JPAQueryFactory(entityManager)
            .selectFrom(QVisit.visit);

    return q.where(QVisit.visit.client.eq(email).or(QVisit.visit.doctor.eq(email))).fetch();
  }

  public void delete(Visit visit) {
    delegate.delete(visit);
  }
}
