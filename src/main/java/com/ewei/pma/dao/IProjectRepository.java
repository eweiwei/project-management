package com.ewei.pma.dao;

import java.util.List;

import com.ewei.pma.dto.ChartData;
import com.ewei.pma.dto.TimeChartData;
import com.ewei.pma.entities.Employee;
import com.ewei.pma.entities.Project;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="apiprojects", path="apiprojects")
public interface IProjectRepository extends PagingAndSortingRepository<Project, Long> {

	@Override
	public List<Project> findAll();
	
	@Query(nativeQuery=true, value="SELECT stage as label, COUNT(*) as value " + 
			"FROM project " + 
			"GROUP BY stage")
	public List<ChartData> getProjectStatus(); 
	
	@Query(nativeQuery=true, value="SELECT name as projectName, start_date as startDate, end_date as endDate"
			+ " FROM project WHERE start_date is not null")
	public List<TimeChartData> getTimeData();

	public Project findByProjectId(long theId);

	// @Query(nativeQuery=true, value="SELECT e FROM employee e " +
	// "LEFT JOIN project_employee pe ON pe.employee_id = e.employee_id " +
	// "WHERE pe.project_id = ?1")
	// public List<Employee> employeeProject(Long theId); 

}
