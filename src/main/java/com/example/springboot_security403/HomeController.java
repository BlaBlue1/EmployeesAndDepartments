package com.example.springboot_security403;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CloudinaryConfig cloudc;

    @RequestMapping("/secure")
    public String secure(Principal principal, Model model){
        String username = principal.getName();
        model.addAttribute("user", userRepository.findByUsername(username));
        return "secure";
    }

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("departments", departmentRepository.findAll());
        return "index";
    }
    @GetMapping("/addDepartment")
    public String addDepartment(Model model){
        model.addAttribute("department", new Department());
        return "departmentForm";
    }
    @RequestMapping("/updateDepartment/{id}")
    public String updateDepartment(@PathVariable("id")long id, Model model){
        model.addAttribute("department", departmentRepository.findById(id).get());
        return "departmentForm";
    }
    @RequestMapping("/deleteDepartment/{id}")
    public String deleteDepartment(@PathVariable("id")long id){
        departmentRepository.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/processDepartment")
    public String processDepartment(@ModelAttribute Department department){
        departmentRepository.save(department);
        return "redirect:/";
    }
    @RequestMapping("/allDepartments")
    public String allDepartments(Model model){
        model.addAttribute("departments", departmentRepository.findAll());
        return "allDepartments";
    }

    @GetMapping("/addEmployee")
    public String addEmployee(Model model){
        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", departmentRepository.findAll());
        return "employeeForm";
    }
    @RequestMapping("/updateEmployee/{id}")
    public String updateEmployee(@PathVariable("id") long id, Model model){
        model.addAttribute("employee", employeeRepository.findById(id).get());
        model.addAttribute("departments", departmentRepository.findAll());
        return "employeeForm";
    }
    @RequestMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable("id")long id){
        employeeRepository.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/processEmployee")
    public String processEmployee(@ModelAttribute Employee employee,
                                  @RequestParam("file") MultipartFile file) {
        if (file.isEmpty() && (employee.getHeadshot() == null) || employee.getHeadshot().isEmpty()) {
            employee.setHeadshot("https://res.cloudinary.com/dlxiq5scx/image/upload/v1628703604/bsgzrviuqihneyqkwsgf.jpg");
        } else if (!file.isEmpty()) {
            try {
                Map uploadResult = cloudc.upload(file.getBytes(),
                        ObjectUtils.asMap("resourcetype", "auto"));
                employee.setHeadshot(uploadResult.get("url").toString());
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/addEmployee";
            }
        }
            employeeRepository.save(employee);
            return "redirect:/";
    }

    @RequestMapping("/allEmployees")
    public String allEmployees(Model model){
        model.addAttribute("employees", employeeRepository.findAll());
        return "allEmployees";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    @RequestMapping("/admin")
    public String admin(){
        return "admin";
    }
    @RequestMapping("/logout")
    public String logout(){
        return "redirect:/login?logout=true";
    }

}
