/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package com.bugtracker.SpringBootRestApp.model;
import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Submitter extends UserAccount
{

  //Submitter Associations
	@ManyToMany
	private Set<Project> projects;
	@OneToMany
	private Set<Ticket> tickets;

  
}