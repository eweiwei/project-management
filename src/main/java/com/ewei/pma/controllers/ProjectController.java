package com.ewei.pma.controllers;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ewei.pma.dto.TimeChartData;
import com.ewei.pma.entities.Comment;
import com.ewei.pma.entities.Employee;
import com.ewei.pma.entities.Project;
import com.ewei.pma.entities.Ticket;
import com.ewei.pma.security.AppEmployee;
import com.ewei.pma.security.LoggedInEmployee;
import com.ewei.pma.services.EmployeeService;
import com.ewei.pma.services.ProjectService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/projects")
public class ProjectController {
	
	@Autowired
	ProjectService proService;
	
	@Autowired
	EmployeeService empService;
	
	@GetMapping
	public String displayProjects(Model model) {
		List<Project> projects = proService.getAll();
		model.addAttribute("projects", projects);
		return "projects/list-projects";
	}
	
	@GetMapping("/new")
	public String displayProjectForm(Model model) {
		
		Project aProject = new Project();
		Iterable<Employee> employees = empService.getAll();
		model.addAttribute("project", aProject);
		model.addAttribute("allEmployees", employees);
		
		return "projects/new-project";
	}
	
	@PostMapping("/save")
	public String createProject(Project project, @RequestParam List<Long> employees, Model model) {
		
		proService.save(project);
		
		// use a redirect to prevent duplicate submissions
		return "redirect:/projects ";
		
	}
	
	@GetMapping("/timelines")
	public String displayProjectTimelines(Model model) throws JsonProcessingException {
		
		List<TimeChartData> timelineData = proService.getTimeData();
		
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonTimelineString = objectMapper.writeValueAsString(timelineData);

		System.out.println("---------- project timelines ----------");
		System.out.println(jsonTimelineString);
		
		model.addAttribute("projectTimeList", jsonTimelineString);
		
		return "projects/project-timelines";
	}

	@GetMapping("/update")
	public String displayProjectUpdateForm(@RequestParam("id") long theId, Model model) {
		
		Project thePro = proService.findByProjectId(theId);
		model.addAttribute("project", thePro);

		Iterable<Employee> employees = empService.getAll();
		model.addAttribute("allEmployees", employees);
		
		return "projects/update-project";
	}

	@PostMapping("/save-update") 
	public String updateProjectTest(@RequestParam("id") long projectId, @RequestParam List<Long> employees, Model model, Project project) {

		// Iterable<Employee> employees = empService.getAll();
		// model.addAttribute("allEmployees", employees);

		Project pro = proService.findByProjectId(projectId);

		if (project.getName() != null) {
			pro.setName(project.getName());
		}
		if(project.getStage() != null) {
			pro.setStage(project.getStage());
		}
		if(project.getDescription() != null) {
			pro.setDescription(project.getDescription());
		}
		if (project.getStartDate() != null) {
			pro.setStartDate(project.getStartDate());
		}
		if (project.getEndDate() != null) {
			pro.setEndDate(project.getEndDate());
		}
		
		pro.setEmployees(project.getEmployees());

		proService.save(pro);

		return "redirect:/projects";
	}

	@GetMapping("delete")
	public String deleteProject(@RequestParam("id") long theId, Model model) {
		Project thePro = proService.findByProjectId(theId);
		proService.delete(thePro);
		return "redirect:/employees";
	}

	@GetMapping("/details")
	public String displayProjectDetails(@RequestParam("id") long projectId, Model model) {
		Project thePro = proService.findByProjectId(projectId);
		model.addAttribute("project", thePro);

		List<Employee> employees = thePro.getEmployees();
		model.addAttribute("allEmployees", employees);

		List<Ticket> tickets = thePro.getTickets();
		model.addAttribute("allTickets", tickets);

		List<Comment> comments = thePro.getComments();
		model.addAttribute("allComments", comments);

		return "projects/details";
	}

	@GetMapping("/your-projects")
	public String displayYourProjectsBuffer(@LoggedInEmployee AppEmployee appEmp, Model model) {
		
		List<Project> pro = empService.findByUsername(appEmp.getUsername()).getProjects();
		model.addAttribute("pro", pro);

		// appEmp.getAuthorities().size();
		// Employee emp = appEmp.getEmployee();

		// model.addAttribute("emp", emp);

		return "projects/your-projects";
	}

}
