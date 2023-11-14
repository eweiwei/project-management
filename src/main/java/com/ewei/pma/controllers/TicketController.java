package com.ewei.pma.controllers;

import java.util.List;

import com.ewei.pma.dto.TicketProject;
import com.ewei.pma.entities.Comment;
import com.ewei.pma.entities.Employee;
import com.ewei.pma.entities.Project;
import com.ewei.pma.entities.Ticket;
import com.ewei.pma.security.AppEmployee;
import com.ewei.pma.security.LoggedInEmployee;
import com.ewei.pma.services.EmployeeService;
import com.ewei.pma.services.ProjectService;
import com.ewei.pma.services.TicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/tickets")
public class TicketController {
    
    @Autowired
    TicketService ticketService;

    @Autowired
    ProjectService proService;

	@Autowired
	EmployeeService empService;

    @GetMapping
    public String displayTickets(Model model) {
        List<TicketProject> ticketProject = ticketService.ticketProjects();
		model.addAttribute("ticketProject", ticketProject);
        
        List<Ticket> tickets = ticketService.getAll();
		model.addAttribute("tickets", tickets);
        
        return "tickets/list-tickets.html";
    }

    @GetMapping("/new")
    public String displayNewTicketForm(Model model, @LoggedInEmployee AppEmployee appEmp) {

        Iterable<Project> projects = proService.getAll();
		Employee employee = appEmp.getEmployee();
        Ticket aTicket = new Ticket();

		model.addAttribute("employees", employee);
        model.addAttribute("allProjects", projects);
        model.addAttribute("ticket", aTicket);

        return "tickets/new-ticket.html";
    }    

    @PostMapping("/save")
    public String createTicket(Model model, @RequestParam List<Long> projects, Ticket ticket, @RequestParam Employee employee) {
        
        ticketService.save(ticket);

        return "redirect:/tickets";
    }

    @GetMapping("/update")
	public String displayTicketUpdateForm(@RequestParam("id") long theId, Model model) {
		
		Ticket theTicket = ticketService.findByTicketId(theId);
		model.addAttribute("ticket", theTicket);

		Iterable<Project> projects = proService.getAll();
		model.addAttribute("allProjects", projects);
		
		return "tickets/update-ticket";
	}

	@PostMapping("/save-update") 
	public String updateProjectTest(@RequestParam("id") long ticketId, @RequestParam List<Long> projects, @RequestParam List<Long> employees, Model model, Ticket ticket) {

		// Iterable<Employee> employees = empService.getAll();
		// model.addAttribute("allEmployees", employees);

		Ticket tick = ticketService.findByTicketId(ticketId);

		if (ticket.getTitle() != null) {
			tick.setTitle(ticket.getTitle());
		}
		if(ticket.getStage() != null) {
			tick.setStage(ticket.getStage());
		}
		if(ticket.getDescription() != null) {
			tick.setDescription(ticket.getDescription());
		}
		if (ticket.getPostDate() != null) {
			tick.setPostDate(ticket.getPostDate());
		}
		if (ticket.getDeadlineDate() != null) {
			tick.setDeadlineDate(ticket.getDeadlineDate());
		}
		
		tick.setProjects(ticket.getProjects());

		ticketService.save(tick);

		return "redirect:/projects";
	}

    @GetMapping("delete")
	public String deleteTicket(@RequestParam("id") long theId, Model model) {
		Ticket theTicket = ticketService.findByTicketId(theId);
		ticketService.delete(theTicket);
		return "redirect:/tickets";
	}

	@GetMapping("/details")
	public String displayTicketDetails(@RequestParam("id") long ticketId, Model model) {
		Ticket theTick = ticketService.findByTicketId(ticketId);
		model.addAttribute("ticket", theTick);

		Employee employee = theTick.getEmployee();
		model.addAttribute("allEmployees", employee);

		List<Project> projects = theTick.getProjects();
		model.addAttribute("allProjects", projects);

		List<Comment> comments = theTick.getComments();
		model.addAttribute("allComments", comments);

		return "tickets/details";
	}
	
}
