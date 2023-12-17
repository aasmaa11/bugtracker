/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package com.bugtracker.SpringBootRestApp.model;
import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public abstract class User
{

  //User Attributes
  private String username;
  private String email;
  private String password;
  private String firstName;
  private String lastName;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  //User Associations
  @OneToMany
  private Set<Comment> comments;


}