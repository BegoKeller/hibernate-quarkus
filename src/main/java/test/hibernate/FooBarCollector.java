package test.hibernate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity(name = "FooBarCollector")
@Table(name = "FooBarCollector")
public class FooBarCollector {
  @Id
  @GeneratedValue
  Long id;

  @OneToOne(mappedBy = "fooBarCollector")
  Bar bar;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Bar getBar() {
    return bar;
  }

  public void setBar(Bar bar) {
    this.bar = bar;
    if(this.bar != null) {
      bar.setFooBarCollector(this);
    }
  }
}
