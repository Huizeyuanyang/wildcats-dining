package com.zhy.onlineorder.controller;

import com.zhy.onlineorder.entity.CustomerEntity;
import com.zhy.onlineorder.model.CustomerDto;
import com.zhy.onlineorder.model.RegisterBody;
import com.zhy.onlineorder.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/signup")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void signUp(@RequestBody RegisterBody body) {
        customerService.signUp(body.email(), body.password(), body.firstName(), body.lastName());
    }
    @GetMapping("/me")
    public CustomerDto getMe(@AuthenticationPrincipal User user) {
        CustomerEntity customer = customerService.getCustomerByEmail(user.getUsername());
        return new CustomerDto(customer);
    }
}
