package com.test;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.jpa.SpecHints;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import test.hibernate.Bar;
import test.hibernate.FooBarCollector;

@QuarkusTest
@TestTransaction
class HibernateTest {

  @PersistenceContext
  EntityManager em;

  @Test
  public void test() {
    Long entityId = createEntity();
    em.flush();
    em.clear();
    checkIfAllFieldsAreFilled(entityId);
  }

  private Long createEntity() {
    var fooBarCollector = new FooBarCollector();
    var bar = new Bar();
    bar.setId(1337L);
    bar.setInfo("test123");
    em.persist(bar);
    fooBarCollector.setBar(bar);
    em.persist(fooBarCollector);
    return fooBarCollector.getId();
  }

  private void checkIfAllFieldsAreFilled(Long id) {
    var graph = this.em.createEntityGraph(FooBarCollector.class);
    graph.addAttributeNodes("bar");
    var persistedEntity = em.createQuery("select t from FooBarCollector t where t.id=" + id, FooBarCollector.class)
        .setHint(SpecHints.HINT_SPEC_FETCH_GRAPH, graph)
        .getSingleResult();
    Assertions.assertNull(persistedEntity.getBar().getFoo());
  }
}
