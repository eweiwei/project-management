// package com.ewei.pma.api.controllers;

// import com.ewei.pma.dao.ITicketRepository;
// import com.ewei.pma.entities.Ticket;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.dao.EmptyResultDataAccessException;
// import org.springframework.http.HttpStatus;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PatchMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.ResponseStatus;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// @RequestMapping("/app-api/tickets")
// public class TicketApiController {
    
//     @Autowired
// 	ITicketRepository ticketRepo;
	
// 	@GetMapping
// 	public Iterable<Ticket> getTickets(){
// 		return ticketRepo.findAll();
// 	}
	
// 	@GetMapping("/{id}")
// 	public Ticket getTicketById(@PathVariable("id") Long id) {
// 		return ticketRepo.findById(id).get();
// 	}
	
// 	@PostMapping(consumes = "application/json")
// 	@ResponseStatus(HttpStatus.CREATED)
// 	public Ticket create(@RequestBody Ticket ticket) {
// 		return ticketRepo.save(ticket);
// 	}
	
// 	@PutMapping(consumes = "application/json")
// 	@ResponseStatus(HttpStatus.OK)
// 	public Ticket update(@RequestBody Ticket ticket) {
// 		return ticketRepo.save(ticket);
// 	}
	
// 	@PatchMapping(path="/{id}", consumes= "application/json")
// 	public Ticket partialUpdate(@PathVariable("id") long id, @RequestBody Ticket patchTicket) {
// 		Ticket ticket = ticketRepo.findById(id).get();
		
// 		if(patchTicket.getTitle() != null) {
// 			ticket.setTitle(patchTicket.getTitle());
// 		}
// 		if(patchTicket.getStage() != null) {
// 			ticket.setStage(patchTicket.getStage());
// 		}
// 		if(patchTicket.getDescription() != null) {
// 			ticket.setDescription(patchTicket.getDescription());
// 		}
		
// 		return ticketRepo.save(ticket);
// 	}
	
	
// 	@DeleteMapping("/{id}")
// 	@ResponseStatus(HttpStatus.NO_CONTENT)
// 	public void delete(@PathVariable("id") Long id) {
// 		try {
// 			ticketRepo.deleteById(id);
// 		}
// 		catch(EmptyResultDataAccessException e) {
			
// 		}
// 	}

// }
