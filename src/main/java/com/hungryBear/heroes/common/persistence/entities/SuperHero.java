package com.hungryBear.heroes.common.persistence.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "_hero")
public class SuperHero {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column
  private String name;

}
