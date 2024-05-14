/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package com.bugtracker.SpringBootRestApp.model;
import java.util.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Ticket
{
  public enum TicketStatus { New, Open, InProgress, Resolved, AddInfo }
  public enum TicketType { Bug, FeatureRequest, DocRequest, Other }
  public enum TicketPriority { None, Low, Medium, High }

  //Ticket Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private int id;
  private String title;
  private String description;
  private TicketPriority priority;
  private TicketStatus status;
  private TicketType type;
  @CreationTimestamp
  private String creationDate;
  @UpdateTimestamp
  private String updateDate;

  //Ticket Associations
  @OneToMany(cascade = CascadeType.ALL)
  private Set<TicketAttachment> ticketAttachments;
  @OneToMany(cascade = CascadeType.ALL)
  private Set<TicketHistory> ticketHistories;
  @ManyToMany
  private Set<Developer> assignedDevelopers;
  @OneToMany(cascade = CascadeType.ALL)
  private Set<Comment> comments;
  @ManyToOne
  private Project project;
  @ManyToOne
  private UserAccount submitter;

}