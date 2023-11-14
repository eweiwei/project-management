package com.ewei.pma.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Ticket {
    
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="ticket_seq")
	private long ticketId;

    private String title;

    private String description;

    private String stage;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date postDate;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deadlineDate;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
			   fetch = FetchType.LAZY)
	@JoinTable(name="project_ticket",
	joinColumns=@JoinColumn(name="ticket_id"),
	inverseJoinColumns= @JoinColumn(name="project_id")
			)
	@JsonIgnore
    private List<Project> projects;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
			   fetch = FetchType.LAZY)
	@JoinTable(name="employee_ticket",
	joinColumns=@JoinColumn(name="ticket_id"),
	inverseJoinColumns= @JoinColumn(name="employee_id")
			)
	@JsonIgnore
    private Employee employee;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
			   fetch = FetchType.LAZY)
	@JoinTable(name="comment_ticket",
	joinColumns=@JoinColumn(name="ticket_id"),
	inverseJoinColumns= @JoinColumn(name="comment_id")
			)
	@JsonIgnore
    private List<Comment> comments;
    
    public Ticket() {

    }

    public Ticket(String title, String description, String stage) {
        super();
        this.title = title;
        this.description = description;
        this.stage = stage;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public long getTicketId() {
        return this.ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStage() {
        return this.stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public Date getPostDate() {
        return this.postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public Date getDeadlineDate() {
        return this.deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }


}
