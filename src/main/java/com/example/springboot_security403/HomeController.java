package com.example.springboot_security403;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class HomeController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    PetRepository petRepository;

    @RequestMapping("/secure")
    public String secure(Principal principal, Model model){
        String username = principal.getName();
        model.addAttribute("user", userRepository.findByUsername(username));
        return "secure";
    }

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("owners", ownerRepository.findAll());
        return "index";
    }
    @GetMapping("/addOwner")
    public String addOwner(Model model){
        model.addAttribute("owner", new Owner());
        return "ownerForm";
    }
    @RequestMapping("/updateOwner/{id}")
    public String updateOwner(@PathVariable("id")long id, Model model){
        model.addAttribute("owner", ownerRepository.findById(id).get());
        return "ownerForm";
    }
    @RequestMapping("/deleteOwner/{id}")
    public String deleteOwner(@PathVariable("id")long id){
        ownerRepository.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/processOwner")
    public String processOwner(@ModelAttribute Owner owner){
        ownerRepository.save(owner);
        return "redirect:/";
    }

    @GetMapping("/addPet")
    public String addPet(Model model){
        model.addAttribute("pet", new Pet());
        model.addAttribute("owner", ownerRepository.findAll());
        return "petForm";
    }
    @RequestMapping("/updatePet/{id}")
    public String updatePet(@PathVariable("id") long id, Model model){
        model.addAttribute("pet", petRepository.findById(id).get());
        model.addAttribute("owners", ownerRepository.findAll());
        return "petForm";
    }
    @RequestMapping("/deletePet/{id}")
    public String deletePet(@PathVariable("id")long id){
        petRepository.deleteById(id);
        return "redirect:/";
    }


    @PostMapping("/processPet")
    public String processPet(@ModelAttribute Pet pet){
        petRepository.save(pet);
        return "redirect:/";
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
