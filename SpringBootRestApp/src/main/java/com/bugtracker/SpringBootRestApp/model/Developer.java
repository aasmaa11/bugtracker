/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package com.bugtracker.SpringBootRestApp.model;
import java.util.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Developer extends UserAccount
{

  @ManyToMany
  private Set<Ticket> assignedTickets;

  @ManyToMany
  private Set<Project> projects;
  
  @Override
  public boolean equals(Object o) {
      return this == o;
  }

  @Override
  public int hashCode() {
      return System.identityHashCode(this);
  }

}