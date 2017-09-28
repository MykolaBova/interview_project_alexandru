package com.alex.places.server.db.dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

public class PlaceFinderDAO<T extends Serializable> {
  private Class<T> clazz;

  @Inject
  private Provider<EntityManager> em;

  public PlaceFinderDAO(Class<T> clazz) {
    this.clazz = clazz;
  }

  @Transactional
  public T findOne(Long id) {
    return em().find(clazz, id);
  }

  @Transactional
  public List<T> findAll() {
    return em().createQuery( "from " + clazz.getName()).getResultList();
  }

  @Transactional
  public void save(T entity) {
    em().persist(entity);
  }

  @Transactional
  public void update(T entity) {
    em().merge(entity);
  }

  @Transactional
  public void delete(T entity) {
    em().remove(entity);
  }

  @Transactional
  public void deleteById(Long entityId){
    T entity = findOne(entityId);
    delete(entity);
  }

  protected EntityManager em() {
    return this.em.get();
  }
}
