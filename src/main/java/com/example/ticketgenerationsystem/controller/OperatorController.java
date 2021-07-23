package com.example.ticketgenerationsystem.controller;

import com.example.ticketgenerationsystem.dto.OperatorDTO;
import com.example.ticketgenerationsystem.request.OperatorSignupRequest;
import com.example.ticketgenerationsystem.request.OperatorUpdateRequest;
import com.example.ticketgenerationsystem.response.ResponseBean;
import com.example.ticketgenerationsystem.service.OperatorService;
import com.example.ticketgenerationsystem.validator.OperatorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operator")
public class OperatorController {
    @Autowired
    private OperatorService operatorService;

    @PostMapping
    public ResponseEntity<ResponseBean<Object>> signup(@RequestBody OperatorSignupRequest request) {
        OperatorValidator.validate(request); // validates operator dto as well as corresponding vehicle dto
        operatorService.add(request);
        return new ResponseEntity<>(new ResponseBean<>(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBean<Object>> update(@RequestBody OperatorUpdateRequest request, @PathVariable int id) {
        OperatorValidator.validate(request);
        operatorService.update(request, id);
        return new ResponseEntity<>(new ResponseBean<>(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBean<Object>> delete(@PathVariable int id) {
        operatorService.delete(id);
        return new ResponseEntity<>(new ResponseBean<>(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<OperatorDTO>> getAll() {
        List<OperatorDTO> operatorDTOList = operatorService.getAll();
        return new ResponseEntity<>(operatorDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperatorDTO> getOne(@PathVariable int id) {
        OperatorDTO operatorDTO = operatorService.getOne(id);
        return new ResponseEntity<>(operatorDTO, HttpStatus.OK);
    }
}
