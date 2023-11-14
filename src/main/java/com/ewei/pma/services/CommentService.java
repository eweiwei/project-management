package com.ewei.pma.services;

import java.util.List;

import com.ewei.pma.dao.ICommentRepository;
import com.ewei.pma.entities.Comment;
import com.ewei.pma.entities.Employee;
import com.ewei.pma.entities.Project;
import com.ewei.pma.entities.Ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    
    @Autowired
    ICommentRepository commentRepo;

    public Comment save(Comment comment) {
        return commentRepo.save(comment);
    }

    public List<Comment> getAll() {
        return commentRepo.findAll();
    }

    public void delete(Comment comment) {
        commentRepo.delete(comment);
    }

    public Comment findByEmployee(Employee employee) {
        return commentRepo.findByEmployee(employee);
    }

    public Comment findByTicket(Ticket ticket) {
        return commentRepo.findByTicket(ticket);
    }

    public Comment findByProject(Project project) {
        return commentRepo.findByProject(project);
    }

}
