package com.app.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.config.Response;
import com.app.dtos.EmployeeDetailsDTO;
import com.app.dtos.OrderDTO;
import com.app.dtos.UserContactDetailsDTO;
import com.app.dtos.UserDTO;
import com.app.entities.constants.RoleName;
import com.app.services.EmployeeServiceImpl;
import com.app.services.UserServiceImpl;

@CrossOrigin(origins = "*")
@RestController
public class EmployeeController {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private EmployeeServiceImpl employeeService;

    @PostMapping("/employee/signup")
    public ResponseEntity<?> employeeSignUp(@RequestBody UserDTO userDto) {
        EmployeeDetailsDTO employeeDetailsDTO = new EmployeeDetailsDTO();
        employeeDetailsDTO.setUid(userDto.getUid());
        employeeDetailsDTO.setPanCard(userDto.getPanCard());
        userDto.setRoleId(userService.getUserRoleId(RoleName.EMPLOYEE));
        UserDTO result = userService.saveUser(userDto);
        if (result == null)
            return Response.error("Email already exists !");
        employeeDetailsDTO.setEmployeeId(result.getUserId());
        employeeService.addEmployeeDetails(employeeDetailsDTO);
        return Response.success(result);
    }

    @GetMapping("/employee/getorders")
    public ResponseEntity<?> getAllPlacedOrders() {
        List<OrderDTO> result = employeeService.getAllPlacedOrders();
        if (result == null)
            return Response.error("No orders available !");
        return Response.success(result);
    }

    @PutMapping("/employee/{employeeid}/acceptorder/{orderid}")
    public ResponseEntity<?> acceptOrder(@PathVariable("employeeid") int employeeId, @PathVariable("orderid") int orderId) {
        OrderDTO result = employeeService.acceptOrder(employeeId, orderId);
        if (result == null)
            return Response.error("Unexpected error !");
        return Response.success(result);
    }

    @GetMapping("/employee/acceptedorder/{id}")
    public ResponseEntity<?> getAcceptedOrder(@PathVariable("id") int employeeId) {
        OrderDTO result = employeeService.getAcceptedOrder(employeeId);
        if (result == null)
            return Response.error("Unexpected error !");
        return Response.success(result);
    }

    @PutMapping("/employee/confirmdelivery/{orderid}")
    public ResponseEntity<?> confirmDelivery(@PathVariable("orderid") int orderId) {
        OrderDTO result = employeeService.confirmOrder(orderId);
        if (result == null)
            return Response.error("Unexpected error !");
        return Response.success(result);
    }

    @PutMapping("/employee/reportorder/{orderid}")
    public ResponseEntity<?> reportOrder(@PathVariable("orderid") int orderId) {
        OrderDTO result = employeeService.reportOrder(orderId);
        if (result == null)
            return Response.error("Unexpected error !");
        return Response.success(result);
    }

    @GetMapping("/employee/order/history/{id}")
    public ResponseEntity<?> orderHistory(@PathVariable("id") int employeeId) {
        List<OrderDTO> result = employeeService.employeeOrderHistory(employeeId);
        return Response.success(result);
    }

    @GetMapping("/employee/profile/details/{id}")
    public ResponseEntity<?> getEmployeeDetails(@PathVariable("id") int employeeId) {
        EmployeeDetailsDTO result = employeeService.getEmployeeDetails(employeeId);
        return Response.success(result);
    }

    @PutMapping("/employee/status/available/{id}")
    public ResponseEntity<?> makeAvailable(@PathVariable("id") int employeeId) {
        EmployeeDetailsDTO result = employeeService.statusAvailable(employeeId);
        if (result == null)
            return Response.error("Unexpected error !");
        return Response.success(result);
    }

    @PutMapping("/employee/status/unavailable/{id}")
    public ResponseEntity<?> makeUnvailable(@PathVariable("id") int employeeId) {
        EmployeeDetailsDTO result = employeeService.statusUnavailable(employeeId);
        if (result == null)
            return Response.error("Unexpected error !");
        return Response.success(result);
    }

    @GetMapping("/employee/customer/{id}")
    public ResponseEntity<?> getCustomerDetails(@PathVariable("id") int employeeId) {
        UserContactDetailsDTO result = employeeService.getCustomerContactDetails(employeeId);
        if (result == null)
            return Response.error("Unexpected error !");
        return Response.success(result);
    }

}
