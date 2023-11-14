package com.ewei.pma.dao;

import java.util.List;

import com.ewei.pma.dto.TicketEmployee;
import com.ewei.pma.dto.TicketProject;
import com.ewei.pma.entities.Ticket;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="apitickets", path="apitickets")
public interface ITicketRepository extends PagingAndSortingRepository<Ticket, Long>{
    
    @Override
	public List<Ticket> findAll();

    public Ticket findByTicketId(long theId);

    @Query(nativeQuery=true, value="SELECT project.name as projectName, ticket.title as ticketTitle FROM project JOIN project_ticket ON project.project_id = project_ticket.project_id JOIN ticket ON ticket.ticket_id = project_ticket.ticket_id")
    public List<TicketProject> ticketProjects();

    @Query(nativeQuery=true, value="SELECT employee.firstName as firstName, ticket.title as ticketTitle FROM employee JOIN employee_ticket ON employee.employee_id = employee_ticket.employee_id JOIN ticket ON ticket.ticket_id = employee_ticket.ticket_id")
    public List<TicketEmployee> ticketEmployees();

}
