package com.ewei.pma.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ewei.pma.entities.Employee;
import com.ewei.pma.entities.Project;
import com.ewei.pma.security.AppEmployee;
import com.ewei.pma.security.LoggedInEmployee;
import com.ewei.pma.services.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
	
	@Autowired
	EmployeeService empService;

	@GetMapping
	public String displayEmployees(Model model, @LoggedInEmployee AppEmployee appEmp) {
		Iterable<Employee> employees = empService.getAll();
		model.addAttribute("employees", employees);
		Employee employee = appEmp.getEmployee();
		model.addAttribute("employee", employee);
		return "employees/list-employees";
	}
	
	@GetMapping("/new")
	public String displayEmployeeForm(Model model) {
		
		Employee anEmployee = new Employee();
		
		model.addAttribute("employee", anEmployee);
		
		return "employees/new-employee";
	}
	
	@PostMapping("/save")
	public String createEmployee(Model model, @Valid Employee employee, Errors errors) {
		
		if(errors.hasErrors())
			return "main/new-employee";
		
		// save to the database using an employee crud repository
		empService.save(employee);
		
		return "redirect:/employees";
	}
	
	@GetMapping("/update")
	public String displayEmployeeUpdateForm(@RequestParam("id") long theId, Model model) {
		
		Employee theEmp = empService.findByEmployeeId(theId);
		
		model.addAttribute("employee", theEmp);
		
		return "employees/update-employee";
	}

	@PostMapping("/updated")
	public String updateEmployee(Model model, @Valid Employee patchEmployee, Errors errors) {
		if (errors.hasErrors()) {
			return "employees/update-employee";
		}

		Employee emp = empService.findByEmployeeId(patchEmployee.getEmployeeId());

		if(patchEmployee.getEmail() != null) {
			emp.setEmail(patchEmployee.getEmail());
		}
		if(patchEmployee.getFirstName() != null) {
			emp.setFirstName(patchEmployee.getFirstName());
		}
		if(patchEmployee.getLastName() != null) {
			emp.setLastName(patchEmployee.getLastName());
		}
		if(patchEmployee.getRole() != null) {
			emp.setRole(patchEmployee.getRole());
		}
		
		empService.save(emp);

		return "redirect:/employees";
	}
	
	@GetMapping("delete")
	public String deleteEmployee(@RequestParam("id") long theId, Model model) {
		Employee theEmp = empService.findByEmployeeId(theId);
		empService.delete(theEmp);
		return "redirect:/employees";
	}

	@GetMapping("/details")
	public String displayProjectDetails(@RequestParam("id") long employeeId, Model model) {
		Employee theEmp = empService.findByEmployeeId(employeeId);
		model.addAttribute("employee", theEmp);

		List<Project> projects = theEmp.getProjects();
		model.addAttribute("allProjects", projects);

		return "employees/details";
	}


}