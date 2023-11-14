package com.ewei.pma.controllers;

import com.ewei.pma.dao.ICommentRepository;
import com.ewei.pma.dao.IEmployeeRepository;
import com.ewei.pma.entities.Comment;
import com.ewei.pma.entities.Employee;
import com.ewei.pma.entities.Project;
import com.ewei.pma.entities.Ticket;
import com.ewei.pma.security.AppEmployee;
import com.ewei.pma.security.LoggedInEmployee;
import com.ewei.pma.services.CommentService;
import com.ewei.pma.services.EmployeeAuthService;
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
@RequestMapping("/comments")
public class CommentController {
    
    @Autowired
    CommentService commentService;

    @Autowired
    EmployeeAuthService empAuthService;

    @Autowired
    ProjectService proService;

    @Autowired 
    TicketService tickService;

    @GetMapping("/ticket")
    public String displayCreateTicketComment(@RequestParam("id") long theId, @LoggedInEmployee AppEmployee appEmp, Model model) {
        
        Ticket tickets = tickService.findByTicketId(theId);

        Comment comment = new Comment();

        Employee employees = appEmp.getEmployee();
        
        model.addAttribute("employees", employees);
        model.addAttribute("tickets", tickets);
        model.addAttribute("comment", comment);

        return "comments/new-comment-ticket";
    }

    @GetMapping("/project")
    public String displayCreateProjectComment(@RequestParam("id") long theId, @LoggedInEmployee AppEmployee appEmp, Model model) {
        
        Project projects = proService.findByProjectId(theId);

        Comment comment = new Comment();

        Employee employees = appEmp.getEmployee();

        model.addAttribute("employees", employees);
        model.addAttribute("projects", projects);
        model.addAttribute("comment", comment);

        return "comments/new-comment-project";
    }

    @PostMapping("/save/ticket")
    public String createCommentTicket(@RequestParam Employee employee, @RequestParam Ticket ticket, Model model, Comment comment) {
        
        commentService.save(comment);
    
        return "redirect:/tickets";
    }

    @PostMapping("/save/project")
    public String createCommentProject(@RequestParam Employee employee, @RequestParam Project project, Model model, Comment comment) {
        
        commentService.save(comment);
    
        return "redirect:/projects";
    }

}
