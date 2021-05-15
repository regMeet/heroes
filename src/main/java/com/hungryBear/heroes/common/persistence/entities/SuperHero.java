package com.hungryBear.heroes.common.persistence.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_hero")
public class SuperHero implements Serializable {
  private static final long serialVersionUID = -2798782985920607347L;

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @NonNull
  @Column
  private String name;

}
