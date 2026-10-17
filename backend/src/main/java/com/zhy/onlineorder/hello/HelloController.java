package com.zhy.onlineorder.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String sayHello(){
        return "Hello world";
    }

//    localhost:8080/testPerson/ltx
    @GetMapping("testPerson/{name}")
    public Person getPersonByPath(@PathVariable("name") String name){
        Address homeAddress = new Address("1088 Xueyuan St.", "Shenzhen", "Guangdong", "China");
        Book favouriteBook = new Book("Journey Under the Midnight Sun", "Byakuyako");
        Person person = new Person(name, "Some Little Shit Company", homeAddress, favouriteBook);
        return person;
    }


//    localhost:8080/testPerson?name=zhy
    @GetMapping("testPerson")
    public Person getPersonByQuery(@RequestParam(required = false, defaultValue = "Default") String name){
        Address homeAddress = new Address("811 Emerson St.", "Evanston", "Illinois", "United States");
        Book favouriteBook = new Book("Journey Under the Midnight Sun", "Byakuyako");
        Person person = new Person(
                name,
                "Richest Company",
                homeAddress,
                favouriteBook);
        return person;
    }
}
