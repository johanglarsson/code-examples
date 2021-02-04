package com.samples.springgraphqlservice.games;

import io.leangen.graphql.annotations.GraphQLQuery;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Game {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  @GraphQLQuery(name = "name", description = "Name of the game")
  private String name;

  @Column(name = "company_name")
  private String companyName;

  @OneToMany(mappedBy = "game", cascade = CascadeType.PERSIST)
  private List<Device> devices = new ArrayList<Device>();

  public void addDevice(DeviceName name) {
    Device device = new Device();
    device.setName(name);
    this.devices.add(device);
    device.setGame(this);
  }
}
