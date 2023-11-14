package com.ewei.pma.dao;

import java.util.List;

import com.ewei.pma.entities.Comment;
import com.ewei.pma.entities.Employee;
import com.ewei.pma.entities.Project;
import com.ewei.pma.entities.Ticket;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.repository.PagingAndSortingRepository;

@RepositoryRestResource(collectionResourceRel="apicomments", path="apicomments")
public interface ICommentRepository extends PagingAndSortingRepository<Comment, Long>{
    
    @Override
    public List<Comment> findAll();

    public Comment findByEmployee(Employee employee);

    public Comment findByTicket(Ticket ticket);
    
    public Comment findByProject(Project project);

}
