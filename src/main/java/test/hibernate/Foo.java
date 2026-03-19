package test.hibernate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity(name = "Foo")
@Table(name = "FOO")
public class Foo {
  @Id
  @Column(name = "ID")
  Long id;

  @MapsId
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ID", nullable = false, unique = true)
  Bar bar;

  @Column(name = "INFO")
  String info;

  public void setBar(Bar bar) {
    this.bar = bar;
  }

  public Long getId() {
    return id;
  }

  public Bar getBar() {
    return bar;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }
}
