package com.ewei.pma.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ewei.pma.dao.IEmployeeRepository;
import com.ewei.pma.dto.EmployeeProject;
import com.ewei.pma.entities.Employee;

@Service
@Transactional
public class EmployeeService {

	@Autowired
	IEmployeeRepository empRepo;
	
	public Employee save(Employee employee) {
		return empRepo.save(employee);
	}
	
	public List<Employee> getAll() {
		return empRepo.findAll();
	}
	
	public List<EmployeeProject> employeeProjects(){
		return empRepo.employeeProjects();
	}
	
	public Employee findByEmployeeId(long theId) {
		return empRepo.findByEmployeeId(theId);
	}

	public void delete(Employee theEmp) {
		empRepo.delete(theEmp);
	}

	public Employee findByUsername(String username) {
		return empRepo.findByUsername(username);
	}
	
}
