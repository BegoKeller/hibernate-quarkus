package test.hibernate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.jpa.SpecHints;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    Long id = inTransaction(em -> {
      var fooBarCollector = new FooBarCollector();
      var bar = new Bar();
      bar.setId(1337L);
      bar.setInfo("test123");
      fooBarCollector.setBar(bar);
      em.persist(fooBarCollector);
      return fooBarCollector.getId();
    });

    FooBarCollector loaded = inTransaction(em -> {
      var graph = em.createEntityGraph(FooBarCollector.class);
      graph.addAttributeNodes("bar");
      return em.createQuery("select t from FooBarCollector t where t.id = :id", FooBarCollector.class)
          .setParameter("id", id)
          .setHint(SpecHints.HINT_SPEC_FETCH_GRAPH, graph)
          .getSingleResult();
    });

    assertNotNull(loaded.getBar());
    assertNull(loaded.getBar().getFoo());
  }

  private static <T> T inTransaction(java.util.function.Function<EntityManager, T> task) {
    try (EntityManager em = emf.createEntityManager()) {
      var tx = em.getTransaction();
      try {
        tx.begin();
        T result = task.apply(em);
        tx.commit();
        return result;
      } catch (RuntimeException e) {
        if (tx.isActive()) {
          tx.rollback();
        }
        throw e;
      }
    }
  }
}



