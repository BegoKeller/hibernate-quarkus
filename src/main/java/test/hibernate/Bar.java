package test.hibernate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity(name = "Bar")
@Table(name = "BAR")
public class Bar {
  @Id
  @Column(name = "ID", nullable = false, precision = 19, unique = true)
  Long id;

  @OneToOne(mappedBy = "bar")
  Foo foo;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fooBarCollector", unique = true)
  FooBarCollector fooBarCollector;

  @Column(name = "info")
  String info;

  public void setFoo(Foo foo) {
    this.foo = foo;
    if (foo != null) {
      foo.setBar(this);
    }
  }

  public Long getId() {
    return id;
  }

  public Foo getFoo() {
    return foo;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  public FooBarCollector getFooBarCollector() {
    return fooBarCollector;
  }

  public void setFooBarCollector(FooBarCollector fooBarCollector) {
    this.fooBarCollector = fooBarCollector;
  }
}
