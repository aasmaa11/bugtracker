/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package com.bugtracker.SpringBootRestApp.model;
import java.util.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.InheritanceType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance
public abstract class UserAccount
{

  //User Attributes
  private String username;
  private String email;
  private String password;
  private String firstName;
  private String lastName;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="idGenerator")
	@SequenceGenerator(name="mydb", sequenceName="idGenerator")
	private Long id;

  //User Associations
  @OneToMany
  private Set<Comment> comments;
  
  @OneToMany
  private Set<Ticket> createdTickets;
  
  @OneToMany
  private Set<TicketAttachment> ticketAttachments;

  

}