package com.ewei.pma.services;

import java.util.List;

import com.ewei.pma.dao.ITicketRepository;
import com.ewei.pma.dto.TicketEmployee;
import com.ewei.pma.dto.TicketProject;
import com.ewei.pma.entities.Ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    
    @Autowired
    ITicketRepository ticketRepo;

    public Ticket save(Ticket ticket) {
        return ticketRepo.save(ticket);
    }

    public Ticket findByTicketId(long theId) {
        return ticketRepo.findByTicketId(theId);
    }

    public void delete(Ticket ticket) {
        ticketRepo.delete(ticket);
    }

    public List<TicketProject> ticketProjects() {
        return ticketRepo.ticketProjects();
    }

    public List<TicketEmployee> ticketEmployees() {
        return ticketRepo.ticketEmployees();
    }

    public List<Ticket> getAll() {
        return ticketRepo.findAll();
    }

}
