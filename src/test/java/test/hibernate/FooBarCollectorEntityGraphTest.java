package test.hibernate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.jpa.SpecHints;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class FooBarCollectorEntityGraphTest {

  private static EntityManagerFactory emf;

  @BeforeAll
  static void setUp() {
    emf = Persistence.createEntityManagerFactory("hibernate-test-pu");
  }

  @AfterAll
  static void tearDown() {
    if (emf != null) {
      emf.close();
    }
  }
  @Test
  void loadWithEntityGraphLeavesBarFooNullWhenNoFooExists() {
    try (EntityManager em = emf.createEntityManager()) {
      var tx = em.getTransaction();
      tx.begin();
      var fooBarCollector = new FooBarCollector();
      var bar = new Bar();
      bar.setId(1337L);
      bar.setInfo("test123");
      fooBarCollector.setBar(bar);
      em.persist(fooBarCollector);
      em.flush();
      em.clear();
      var graph = em.createEntityGraph(FooBarCollector.class);
      graph.addAttributeNodes("bar");
      var loaded = em.createQuery("select t from FooBarCollector t where t.id = :id", FooBarCollector.class)
          .setParameter("id", fooBarCollector.getId())
          .setHint(SpecHints.HINT_SPEC_FETCH_GRAPH, graph)
          .getSingleResult();
      assertNull(loaded.getBar().getFoo());
      tx.commit();
    }

  }
}



