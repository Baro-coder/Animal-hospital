package pl.pw.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "visits")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Visit {

  @Id
  @Column(name = "id")
  private String id;

  //@OneToOne(fetch = FetchType.EAGER)
  @Column(name = "client_mail")
  private String client;

  //@OneToOne(fetch = FetchType.EAGER)
  @Column(name = "doctor_mail")
  private String doctor;

  @Column(name = "animal_age")
  private float animalAge;

  @Column(name = "animal_breed")
  private String animalBreed;

  @Column(name = "time")
  private Instant time;

  @Column(name = "description")
  private String description;
}
