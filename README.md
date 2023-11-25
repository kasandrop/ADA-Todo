# todo-api







# Project summary
## Product Description.
### Brief overview
We are going to create a to do application. It will help manage our daily tasks, which can be categorised into groups such as Personal, Work, or Household.
Therefore, our to do app will have the following components:
Tasks: Each task has a name, description, due date, and a completion status (yes or no).
Labels: There can be Personal, Work, Household label.
Relationship: Each task can be associated with one or more labels, indicating the context or category of the task. This is a one-to-many relationship from tasks to labels.

### Functionality
1. Task Management:Create, read, update, and delete (CRUD) tasks.
2. Label Management:Create, read, update, and delete (CRUD) label.
3. Search task by a label
4. Search task by a name
5. List all labels.
6. List all tasks.
7. Maybe filtering tasks by their labels [ Depends on my mood]

### Api endpoints for tasks and labels:



## Team Description.
[Elaborate on the target audience of your project/product, link out to additional resources]
## Tools used.
### For the Backend 
- Programming language Java version 19
- Framework: Spring Boot
- Build Tool: Maven
- Database: H2 (file based)

The choice of Java 19, Spring Boot, and Maven for my backend development is primarily driven by my familiarity with these tools. These technologies are well-established in the industry, offering robust features and extensive community support, making them a reliable choice for building efficient and scalable applications.  Spring Boot provides a simplified approach to developing, allowing for rapid prototyping and development. Maven, a powerful project management tool, helps build and manage any Java-based project, ensuring consistency in project builds.

### For the frontend
## Instructions on how to run your application.
__requirements:__
1.   java version 17
2.  Apache Maven 3.3 or above
3. h2 database:username:MARCIN pass:MARCIN1
to run a backend :
`mvn compile exec:java`
to stop the service : ctrl+C

[TO MANAGE DATABASE](http://localhost:8091/h2-console)



## Test Methodologies and Tools.

## Coding Best Practices.

## CI Pipeline

## Standards

## Performance and Accessibility audit.

## Critically analyse the results.


