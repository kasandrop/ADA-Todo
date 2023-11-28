# Project summary
![](database.jpg) 
## Product Description.
### Brief overview
We are going to create a to do application.
It will help manage our daily tasks, which can be categorised into groups such as Personal, Work or Household.
Therefore, our to do app will have the following components:
Tasks: Each task has a name, description,  and a completion status (yes or no).
Labels: There can be Personal, Work, Household label.
Relationship: Each task must be associated with one only  label, indicating the context or category of the task.
This is a one-to-many relationship from tasks to labels.
The same label can have zero or more tasks. 

### Functionality
1. Task Management:Create, read, update, and delete (CRUD) tasks.
2. Label Management:Create, read, update, and delete (CRUD) label.
3. Search task by a label
4. Search task by a name
5. List all labels.
6. List all tasks.
7. Maybe filtering tasks by their labels [ Depends on my mood]

### Requirements
1. Label names must be unique. If a user attempts to create a label with a name that already exists, the creation process will fail. The user will then receive an error message indicating that the chosen label name is already in use
2. A label can be created independently and is not required to be associated with any task.
3. However, each task must be associated with an existing label. A task cannot be created with a non-existent label.
4. When creating a task, providing a description is optional. However, a name is mandatory. 
   If a name is not provided, the task creation process will not proceed, and an error message will be displayed 
   to inform the user about the necessity of providing a name.
5. Upon creation, the ‘completion_status’ field of a task is always set to false, indicating that the task is not yet completed.
   This ensures that every new task starts in an incomplete state.

### Error Handling and Messages

 
| Error                                   | When it occurs                                                                        | Message to display                                                                               | Implication                                                                                                                              |
|-----------------------------------------|---------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------|
| Task creation without a name            | When a user tries to create a task without providing a name.                          | "Task creation failed: A name is required for each task."                                        | The task will not be created until a name is provided. This ensures that all tasks can be properly identified.                           |
| Label creation with a duplicate name    | When a user tries to create a label with a name that already exists.                  | "Label creation failed: The name you've chosen is already in use. Please choose a unique name."  | The label will not be created until a unique name is provided. This ensures that all labels can be uniquely identified.                  |
| Task creation with a non-existent label | When a user tries to create a task with a label that doesn't exist.                   | "Task creation failed: The label you've chosen does not exist. Please choose an existing label." | The task will not be created until an existing label is chosen. This ensures that all tasks are associated with valid labels.            |
| Unknown error                           | When an unexpected error occurs that doesn't match any of the known error conditions. | "An unknown error occurred. Please try again."                                                   | The operation failed due to an unexpected issue. The user is advised to try the operation again or contact support if the error persists |

### Api endpoints for tasks and labels:

These  can be access by accessing [OpenAPI definition](http://localhost:8091/swagger-ui/index.html)



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
[tbw]
## Instructions on how to run your application.
__Requirements to be able to run the app:__
1. java version 17
2. Apache Maven 3.3 or above
3. h2 database:username:MARCIN pass:MARCIN1 [TO MANAGE DATABASE](http://localhost:8091/h2-console)


The Spring boot backend service can be run with the following command :
`mvn compile exec:java`
to stop the service : ctrl+C





## Test Methodologies and Tools.

## Coding Best Practices.
Coding best practices are a set of guidelines designed to improve the quality of software development
and make the code more maintainable, scalable, and readable.
Therefore we use tool Checkstyle, which will help achieve the following:

### Consistent Naming Conventions: 
This makes the code easier to read and understand. 
It includes using clear descriptive names for variable, functions, and classes, etc.

### Code Formatting and Organization:
This includes proper indentation, spacing and grouping of code. 
Tools like linters and formatters can help enforce these rules.

### Commenting and Documentation: 
Comments should explain why certain decisions were made in the code. 
Documentation should explain what the code does, how to use it, and any dependencies it has.

### Error Handling: 
This involves anticipating potential problems and handling them gracefully, 
often through the use of try/catch blocks or returning meaningful error messages.

Checkstyle is a development tool that helps code adhere to the above  standards. 
It has been included and configured in the Maven dependencies to automatically check
for style issues during the build process. If the code does not adhere to the specified rules, 
the build will fail.
The rules -- which were written by Google-- are described in xml file.
This ensures that all code meets the team’s agreed-upon standards before
it is deployed, making Checkstyle a valuable tool for maintaining code quality in a project.



## CI Pipeline

## Standards

## Performance and Accessibility audit.

## Critically analyse the results.


