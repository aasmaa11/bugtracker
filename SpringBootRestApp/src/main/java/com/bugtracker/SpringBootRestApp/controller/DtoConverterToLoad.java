package com.bugtracker.SpringBootRestApp.controller;

import java.util.EnumMap;

public class DtoConverterToLoad {
    public enum Loads {
    	Comments,
    	CreatedTickets,
    	TicketAttachments,
    	Ticket,
    	Commenter,
    	AssignedTickets,
    	Projects,
    	ProjectManager,
    	AssignedDevelopers,
    	Tickets,
    	Creator,
    	TicketHistories,
    	Submitter,
    	Project
       
    }

    private EnumMap<Loads, DtoConverterToLoad> loads;

    /**
     * Create a new, empty DtoConverterToLoad. The empty ToLoad will not load anything, keys/values
     * need to be inserted
     *
     * @return a new DtoConverterToLoad
     */
    public DtoConverterToLoad() {
        loads = new EnumMap<Loads, DtoConverterToLoad>(Loads.class);
    }

    /**
     * Indicates a key should be loaded for that Dto conversion. The value will be passed into the
     * Dto conversion for the loaded field; specifying a DtoConverterToLoad with additional keys can
     * recursively load more values in that field.
     *
     * @param k, a member of the Loads enum, the field which should be loaded in the DtoConverter
     * @param kloads, the fields which should be loaded when the object (or list of objects)
     *     represented by the field k is converted; can be null if there are no additional
     *     associations to load
     * @return the DtoConverter this; this is done to allow better inlining when creating these,
     *     rather than requiring each converter be seperately declared as a variable, then modified,
     *     then used
     */
    public DtoConverterToLoad put(Loads k, DtoConverterToLoad kloads) {
        loads.put(k, kloads);
        return this;
    }
    /**
     * Gets the fields to load for DtoConversion when converting a particular field
     *
     * @param k, the Loads field to be converted
     * @return a DtoConverterToLoad of the fields to load, or null
     */
    public DtoConverterToLoad get(Loads k) {
        return loads.get(k);
    }

    /**
     * @param k, a Loads field
     * @return boolean of whether the key is in this particular DtoConverterToLoads structure
     */
    public boolean has(Loads k) {
        return loads.containsKey(k);
    }
}
