package com.nisum.api.dao;

import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public abstract class GenericDao<T> {

  @PersistenceContext
  private EntityManager entityManager;

  public Session getCurrentSession() {
    return entityManager.unwrap(Session.class);
  }

  public List<T> findAllEntity(Class<T> clazz) {
    CriteriaBuilder cb = getCurrentSession().getCriteriaBuilder();
    CriteriaQuery<T> cq = cb.createQuery(clazz);

    cq.from(clazz);

    List<T> resultList = getCurrentSession().createQuery(cq).getResultList();

    return resultList;
  }

  public Optional<T> findOneEntity(String attributeId, String value, Class<T> clazz) {
    CriteriaBuilder cb = getCurrentSession().getCriteriaBuilder();
    CriteriaQuery<T> cq = cb.createQuery(clazz);
    Root<T> entityRoot = cq.from(clazz);

    cq.where(cb.equal(entityRoot.get(attributeId), value));

    return Optional.ofNullable(getCurrentSession().createQuery(cq).uniqueResult());
  }

  public T saveOrUpdateEntity(T t) {
    getCurrentSession().saveOrUpdate(t);
    getCurrentSession().flush();
    getCurrentSession().refresh(t);

    return t;
  }

  public void deleteEntity(T t) {
    getCurrentSession().delete(t);
  }
}
