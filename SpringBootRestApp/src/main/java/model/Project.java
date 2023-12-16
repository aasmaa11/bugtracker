/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package model;
import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
public class Project
{

  //Project Attributes
  private String name;
  private String description;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  //Project Associations
	@ManyToOne
  private ProjectManager projectManager;
	@ManyToMany
  private Set<Submitter> submitters;
	@ManyToOne
  private Admin admin;


}