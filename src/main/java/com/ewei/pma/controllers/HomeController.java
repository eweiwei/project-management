package com.ewei.pma.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ewei.pma.dto.ChartData;
import com.ewei.pma.dto.EmployeeProject;
import com.ewei.pma.dto.TimeChartData;
import com.ewei.pma.entities.Comment;
import com.ewei.pma.entities.Employee;
import com.ewei.pma.entities.Project;
import com.ewei.pma.entities.Ticket;
import com.ewei.pma.security.AppEmployee;
import com.ewei.pma.security.LoggedInEmployee;
import com.ewei.pma.services.EmployeeService;
import com.ewei.pma.services.ProjectService;
import com.ewei.pma.services.TicketService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class HomeController {
	
	@Value("${version}")
	private String ver;
	
	@Autowired
	ProjectService proService;
	
	@Autowired
	EmployeeService empService;

	@Autowired
	TicketService tickService;

	@GetMapping("/")
	public String displayHome(Model model, @LoggedInEmployee AppEmployee appEmployee) throws JsonProcessingException {
		
		model.addAttribute("versionNumber", ver);

		Employee employee = appEmployee.getEmployee();
		model.addAttribute("emp", employee);

		// List<Ticket> tickets = tickService.getAll();
		// model.addAttribute("tickets", tickets);

		List<Employee> employees = empService.getAll();
		model.addAttribute("employees", employees);

		// we are querying the database for projects
		List<Project> projects = proService.getAll();
		model.addAttribute("projectsList", projects);
		
		List<ChartData> projectData = proService.getProjectStatus();
		
		// Lets convert projectData object into a json structure for use in javascript
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = objectMapper.writeValueAsString(projectData);
		// [["NOTSTARTED", 1], ["INPROGRESS", 2], ["COMPLETED", 1]]
		
		model.addAttribute("projectStatusCnt", jsonString);
		
		// we are querying the database for employees
		List<EmployeeProject> employeesProjectCnt = empService.employeeProjects();
		model.addAttribute("employeesListProjectsCnt", employeesProjectCnt);
		
		
		// return "main/index";
		return "main/home";
		
	}

	@GetMapping("/employees-edit")
	public String displayEmployeeTables(Model model, @LoggedInEmployee AppEmployee appEmployee) {

		Iterable<Employee> employees = empService.getAll();
		model.addAttribute("employees", employees);

		Employee employee = appEmployee.getEmployee();
		model.addAttribute("emp", employee);

		return "employees/list-emp-edit.html";
	}

	@GetMapping("/details-edit")
	public String editEmployee(@RequestParam("id") long theId, @LoggedInEmployee AppEmployee appEmployee, Model model) {

		Employee theEmp = empService.findByEmployeeId(theId);

		List<Project> projects = theEmp.getProjects();
		List<Ticket> tickets = new ArrayList<Ticket>();

		for (Project pro : projects) {
			for (Ticket ticket : pro.getTickets()) {
				tickets.add(ticket);
			}
		}

		Employee employee = appEmployee.getEmployee();

		model.addAttribute("emp", employee);
		model.addAttribute("employee", theEmp);
		model.addAttribute("projects", projects);
		model.addAttribute("tickets", tickets);

		return "employees/details-edit";
	}

	@GetMapping("/project-details")
	public String projectDetails(@RequestParam("id") long theId, @LoggedInEmployee AppEmployee appEmp, Model model) {
		
		Project project = proService.findByProjectId(theId);
		List<Comment> comments = project.getComments();
		List<Employee> employees = project.getEmployees();
		Employee employee = appEmp.getEmployee();

		model.addAttribute("emp", employee);
		model.addAttribute("employees", employees);
		model.addAttribute("project", project);
		model.addAttribute("comments", comments);

		return "projects/details-edit";
	}

	@GetMapping("/project-list")
	public String projectList(@LoggedInEmployee AppEmployee appEmp, Model model) {
		
		List<Project> projects = proService.getAll();
		Employee employee = appEmp.getEmployee();

		model.addAttribute("projects", projects);
		model.addAttribute("employee", employee);

		return "projects/list-edit";
	}

	@GetMapping("/ticket-details")
	public String ticketDetails(@RequestParam("id") long theId, @LoggedInEmployee AppEmployee appEmp, Model model) {
		
		// long millis = System.currentTimeMillis();
		// java.util.Date date = new java.util.Date(millis);

		Ticket ticket = tickService.findByTicketId(theId);
		Comment comment = new Comment();
		// comment.setPostDate(date);
		List<Comment> comments = ticket.getComments();
		Employee employee = appEmp.getEmployee();

		model.addAttribute("comment", comment);
		model.addAttribute("emp", employee);
		model.addAttribute("ticket", ticket);
		model.addAttribute("comments", comments);

		return "tickets/ticket-detail-edit";
	}

	@GetMapping("/ticket-list")
	public String ticketList(@LoggedInEmployee AppEmployee appEmp, Model model) {
		
		List<Ticket> tickets = tickService.getAll();
		Employee employee = appEmp.getEmployee();

		model.addAttribute("tickets", tickets);
		model.addAttribute("employee", employee);

		return "tickets/list-tickets-edit";
	}

	@GetMapping("/ticket-edit")
	public String editTicket(@RequestParam("id") long theId, @LoggedInEmployee AppEmployee appEmp, Model model) {

		Ticket ticket = tickService.findByTicketId(theId);

		Employee employee = appEmp.getEmployee();

		model.addAttribute("ticket", ticket);
		model.addAttribute("employee", employee);

		return "tickets/update-ticket-edit";

	}

	@GetMapping("/404")
	public String displayErrorScreen() {
		return "errorpages/404";
	}

	@GetMapping("/blank")
	public String displayBlankScreen() {
		return "main/blank";
	}

	@GetMapping("/buttons")
	public String displayButtonScreen() {
		return "main/buttons";
	}

	@GetMapping("/cards")
	public String displayCards() {
		return "main/cards";
	}
	
	@GetMapping("/charts")
	public String displayCharts() {
		return "main/charts";
	}

	@GetMapping("/forgot-password")
	public String displayForgotPassword() {
		return "security/forgot-password";
	}

	@GetMapping("/loginscreen")
	public String displayLoginScreen() {
		return "security/login-page";
	}

	@GetMapping("/registration")
	public String displayRegisterScreen() {
		return "security/register";
	}

	@GetMapping("/guest")
	public String displayGuestScreen() {
		return "security/guest";
	}

	@GetMapping("/index")
	public String displayIndexScreen(Model model, @LoggedInEmployee AppEmployee appEmployee) throws JsonProcessingException {

		model.addAttribute("versionNumber", ver);

		Employee employee = appEmployee.getEmployee();
		model.addAttribute("emp", employee);

		List<Ticket> tickets = tickService.getAll();
		model.addAttribute("tickets", tickets);

		List<Employee> employees = empService.getAll();
		model.addAttribute("employees", employees);

		// we are querying the database for projects
		List<Project> projects = proService.getAll();
		model.addAttribute("projectsList", projects);
		
		List<ChartData> projectData = proService.getProjectStatus();
		
		// Lets convert projectData object into a json structure for use in javascript
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = objectMapper.writeValueAsString(projectData);
		// [["NOTSTARTED", 1], ["INPROGRESS", 2], ["COMPLETED", 1]]
		
		model.addAttribute("projectStatusCnt", jsonString);
		
		// we are querying the database for employees
		List<EmployeeProject> employeesProjectCnt = empService.employeeProjects();
		model.addAttribute("employeesListProjectsCnt", employeesProjectCnt);

		List<TimeChartData> timelineData = proService.getTimeData();
		
		ObjectMapper secondObjectMapper = new ObjectMapper();
		String jsonTimelineString = secondObjectMapper.writeValueAsString(timelineData);

		System.out.println("---------- project timelines ----------");
		System.out.println(jsonTimelineString);
		
		model.addAttribute("projectTimeList", jsonTimelineString);

		return "main/index";
	}
}
