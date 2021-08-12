package com.example.springboot_security403;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CommandLineRunnerBean implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    public void run(String...args){
        User user = new User("bart", "bart@domain.com", "bart", "Bart", "Simpson", true); //the names and passwords are examples
        Role userRole = new Role("bart", "ROLE_USER");

        userRepository.save(user);
        roleRepository.save(userRole);


        User admin = new User("super", "super@domain.com", "super", "Super", "Hero", true);
        Role adminRole1 = new Role("super", "ROLE_ADMIN");
        Role adminRole2 = new Role("super", "ROLE_USER");

        userRepository.save(admin);
        roleRepository.save(adminRole1);
        roleRepository.save(adminRole2);


        Department department = new Department();
//        Department department2 = new Department();

        department.setName("Human Resource");
//        department2.setName("IT");


        Employee employee = new Employee();
        employee.setFirstName("Bart");
        employee.setLastName("Simpson");
        employee.setJobTitle("Manager");
        employee.setHeadshot("https://res.cloudinary.com/dlxiq5scx/image/upload/v1628703604/bsgzrviuqihneyqkwsgf.jpg");
        employee.setDepartment(department);

//        Employee employee2 = new Employee();
//        employee2.setFirstName("Bethel");
//        employee2.setLastName("Sahle");
//        employee2.setJobTitle("Administrator");
//        employee2.setHeadshot("https://res.cloudinary.com/dlxiq5scx/image/upload/v1628701517/mfoysumawz0n27hbo4tg.jpg");
//        employee2.setDepartment(department2);


        Set<Employee> employees = new HashSet<>();
        employees.add(employee);


//        Set<Employee> empIT = new HashSet<>();
//        empIT.add(employee2);


        department.setEmployees(employees);
//        department2.setEmployees(empIT);


        departmentRepository.save(department);
//        departmentRepository.save(department2);

    }
}
