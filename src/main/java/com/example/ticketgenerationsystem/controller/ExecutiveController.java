package com.example.ticketgenerationsystem.controller;

import com.example.ticketgenerationsystem.constant.Constants;
import com.example.ticketgenerationsystem.convertor.ExecutiveConvertor;
import com.example.ticketgenerationsystem.convertor.PageableConvertor;
import com.example.ticketgenerationsystem.request.ExecutiveSignupRequest;
import com.example.ticketgenerationsystem.request.ExecutiveUpdateRequest;
import com.example.ticketgenerationsystem.request.PasswordUpdateRequest;
import com.example.ticketgenerationsystem.response.ExecutiveObject;
import com.example.ticketgenerationsystem.response.PageableListResponse;
import com.example.ticketgenerationsystem.response.ResponseBean;
import com.example.ticketgenerationsystem.service.ExecutiveService;
import com.example.ticketgenerationsystem.validator.ExecutiveValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/executive")
public class ExecutiveController {
    @Autowired
    private ExecutiveService executiveService;

    @PostMapping
    public ResponseEntity<ResponseBean<Object>> signup(@RequestBody ExecutiveSignupRequest request) {
        ExecutiveValidator.validate(request);
        executiveService.add(request, Constants.roleMap.get(Constants.ROLE_EXECUTIVE));
        return new ResponseEntity<>(new ResponseBean<>(), HttpStatus.OK);
    }

    @PostMapping("/admin")
    public ResponseEntity<ResponseBean<Object>> adminSignup(@RequestBody ExecutiveSignupRequest request) {
        ExecutiveValidator.validate(request);
        executiveService.add(request, Constants.roleMap.get(Constants.ROLE_ADMIN));
        return new ResponseEntity<>(new ResponseBean<>(), HttpStatus.OK);
    }

    // Can be used by admin
    @PutMapping
    public ResponseEntity<ResponseBean<Object>> update(@RequestBody ExecutiveUpdateRequest request) {
        ExecutiveValidator.validate(request);
        executiveService.update(request);
        return new ResponseEntity<>(new ResponseBean<>(), HttpStatus.OK);
    }

    @PutMapping("/password/{id}")
    public ResponseEntity<ResponseBean<Object>> updatePassword(@RequestBody PasswordUpdateRequest request, @PathVariable(name = "id") int executiveId) {
        ExecutiveValidator.validate(request);
        executiveService.updatePassword(request, executiveId);
        return new ResponseEntity<>(new ResponseBean<>(), HttpStatus.OK);
    }

    // except admins
    @GetMapping
    public ResponseEntity<PageableListResponse<ExecutiveObject>> getAllExecutives(@RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize) {
        return new ResponseEntity<>(ExecutiveConvertor.convert(executiveService.findAllByRole(Constants.roleMap.get(Constants.ROLE_EXECUTIVE), PageableConvertor.getPageableObject(pageNo, pageSize))), HttpStatus.OK);
    }

    @GetMapping("/admin")
    public ResponseEntity<PageableListResponse<ExecutiveObject>> getAllAdmins(@RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize) {
        return new ResponseEntity<>(ExecutiveConvertor.convert(executiveService.findAllByRole(Constants.roleMap.get(Constants.ROLE_ADMIN), PageableConvertor.getPageableObject(pageNo, pageSize))), HttpStatus.OK);
    }
}