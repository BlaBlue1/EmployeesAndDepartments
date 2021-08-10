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
    OwnerRepository ownerRepository;

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

        //create an owner
        Owner owner = new Owner();

        owner.setName("Ruth");

        //create pet
        Pet pet = new Pet();
        pet.setName("Jack");
        pet.setType("Dog");
        pet.setDescription("White Chihuahua");
        pet.setOwner(owner);

        Pet pet2 = new Pet();
        pet.setName("Spider");
        pet.setType("Cat");
        pet.setDescription("Cute Abyssinian cat");
        pet.setOwner(owner);


        //add the pet to an empty list
        Set<Pet> pets = new HashSet<>();
        pets.add(pet);
        pets.add(pet2);

        //adding the list of pet to the owner's pets list
        owner.setPets(pets);

        //save owner to the database
        ownerRepository.save(owner);

    }
}
