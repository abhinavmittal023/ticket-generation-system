package com.example.ticketgenerationsystem.service;

import com.example.ticketgenerationsystem.constant.Constants;
import com.example.ticketgenerationsystem.convertor.ExecutiveConvertor;
import com.example.ticketgenerationsystem.convertor.UserConvertor;
import com.example.ticketgenerationsystem.entity.Executive;
import com.example.ticketgenerationsystem.entity.User;
import com.example.ticketgenerationsystem.exception.ApiException;
import com.example.ticketgenerationsystem.repository.ExecutiveRepo;
import com.example.ticketgenerationsystem.request.ExecutiveSignupRequest;
import com.example.ticketgenerationsystem.request.ExecutiveUpdateRequest;
import com.example.ticketgenerationsystem.request.PasswordUpdateRequest;
import com.example.ticketgenerationsystem.util.HashGenerator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class ExecutiveService {
    @Autowired
    private ExecutiveRepo executiveRepo;
    @Autowired
    private UserService userService;

    @Transactional
    public Executive add(ExecutiveSignupRequest request, int role) {
        try {
            User user = userService.add(UserConvertor.convert(request, role));
            return executiveRepo.save(ExecutiveConvertor.convert(request, user));
        } catch (DataIntegrityViolationException e) {
            throw new ApiException("400", Constants.INVALID_REQUEST_PARAMETERS);
        } catch (Exception e) {
            throw new ApiException("500", e.getMessage());
        }
    }

    public List<Executive> findAll(int role) {
        try {
            List<Executive> executiveList = new ArrayList<>();
            List<User> userList = userService.findAll(role);
            for (User user : userList) {
                executiveList.add(executiveRepo.findByUser(user));
            }
            return executiveList;
        } catch (Exception e) {
            throw new ApiException("400", e.getMessage());
        }
    }

    public Page<Executive> findAllByRole(int role, Pageable pageable) {
        try {
            return executiveRepo.findAllByRole(role, pageable);
        } catch(DataIntegrityViolationException e) {
            throw new ApiException("400", Constants.INVALID_REQUEST_PARAMETERS);
        } catch(Exception e) {
            throw new ApiException("500", e.getMessage());
        }
    }

    // Todo: change role - at least one admin should be present
    public Executive update(ExecutiveUpdateRequest request) {
        try {
            Optional<Executive> executiveOptional = executiveRepo.findById(request.getExecutiveId());
            if (!executiveOptional.isPresent()) {
                throw new ApiException("400", Constants.INVALID_REQUEST_PARAMETERS);
            }
            Executive executive = executiveOptional.get();
            if (request.getNewMobileNo() != null) {
                executive.setMobileNo(request.getNewMobileNo());
            }
            if (request.getNewLocation() != null ){
                executive.setLocation(request.getNewLocation());
            }
            if (request.getNewPassword() != null) {
                executive.getUser().setPassword(HashGenerator.getMd5(request.getNewPassword()));
                userService.update(executive.getUser());
            }
            return executiveRepo.save(executive);
        } catch(Exception e) {
            throw new ApiException("500", e.getMessage());
        }
    }

    public Executive updatePassword(PasswordUpdateRequest request, int executiveId) {
        try {
            Optional<Executive> executiveOptional = executiveRepo.findById(executiveId);
            if (!executiveOptional.isPresent()) {
                throw new ApiException("400", Constants.INVALID_REQUEST_PARAMETERS);
            }
            Executive executive = executiveOptional.get();
            if(!HashGenerator.getMd5(request.getOldPassword()).equals(executive.getUser().getPassword())) {
                throw new ApiException("400", "Old Passwords Mismatch");
            }
            executive.getUser().setPassword(HashGenerator.getMd5(request.getNewPassword()));
            userService.update(executive.getUser());
            return executive;
        } catch (Exception e) {
            throw new ApiException("500", e.getMessage());
        }
    }
}
