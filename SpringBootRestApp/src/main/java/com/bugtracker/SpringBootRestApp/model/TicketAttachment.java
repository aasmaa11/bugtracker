/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package com.bugtracker.SpringBootRestApp.model;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class TicketAttachment
{

  //TicketAttachment Attributes
	  @Id
	  @GeneratedValue(strategy = GenerationType.SEQUENCE)
	  private int id;
  private String file;
  private String notes;
  @CreationTimestamp
  private String creationDate;

  //TicketAttachment Associations
  @ManyToOne
  private Ticket ticket;

}