# project-management
Fully functional, scalable webpage using Spring Boot and PostgreSQL as the database. Includes an entire security system and interconnected management of projects, employees, and tickets. Utilizes technologies such as Spring 5, Boot 2, JPA, AOP, Web MVC, RESTful APIs, Thymeleaf, and other Full Stack tools.

This webpage is backed by a PostgreSQL database. Users can create individual user accounts and be assigned different roles that correspond to different levels of administration and permissions. User Accounts are connected to Employee entities and can be assigned to different Project entities. User Accounts are backed by a complex Spring Security system including both Authentication and Authorization. User Accounts with the specified roles can leave comments and submit tickets to projects. Employee entities can work together on tickets and leave their own comments on their corresponding Projects.

Interactive tools like project timelines and graphs of employee contributions are available to User Accounts with administrative roles.
