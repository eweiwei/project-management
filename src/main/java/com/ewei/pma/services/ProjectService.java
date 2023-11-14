package com.ewei.pma.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewei.pma.dao.IProjectRepository;
import com.ewei.pma.dto.ChartData;
import com.ewei.pma.dto.TimeChartData;
import com.ewei.pma.entities.Employee;
import com.ewei.pma.entities.Project;

@Service
public class ProjectService {

	@Autowired
	IProjectRepository proRepo;
	
	public Project save(Project project) {
		return proRepo.save(project);
	}

	public Project findByProjectId(long theId) {
		return proRepo.findByProjectId(theId);
	}

	public void delete(Project thePro) {
		proRepo.delete(thePro);
	}
	
	public List<Project> getAll() {
		return proRepo.findAll();
	}
	
	public List<ChartData> getProjectStatus() {
		return proRepo.getProjectStatus();
	}
	
	public List<TimeChartData> getTimeData(){
		return proRepo.getTimeData();
	}

	// public List<Employee> getEmployeeProject(Long theId) {
	// return proRepo.employeeProject(theId);
	// }
	
}
