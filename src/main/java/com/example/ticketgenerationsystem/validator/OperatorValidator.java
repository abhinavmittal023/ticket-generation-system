package com.example.ticketgenerationsystem.validator;

import com.example.ticketgenerationsystem.constant.Constants;
import com.example.ticketgenerationsystem.request.OperatorSignupRequest;
import com.example.ticketgenerationsystem.request.OperatorUpdateRequest;
import com.example.ticketgenerationsystem.request.VehicleAddRequest;
import com.example.ticketgenerationsystem.exception.ApiException;
import java.util.regex.*;

public class OperatorValidator {
    public static void validate(OperatorSignupRequest request) {
        // First Name must be non null and contain alphabets only
        if(request.getFirstName() == null || request.getFirstName() == "") {
            throw new ApiException("400","First Name Required");
        }else if(!Pattern.matches(Constants.ALPHABET_REGEX, request.getFirstName())) {
            System.out.println(request);
            throw new ApiException("400","First Name must contain alphabets only");
        }

        // Last Name must be contain alphabets only
        if(request.getLastName() != null && !Pattern.matches(Constants.ALPHABET_REGEX, request.getLastName())) {
            throw new ApiException("400","Last Name must contain alphabets only");
        }

        // Email must be non null
        if(request.getEmailId() == null || request.getEmailId() == "") {
            throw new ApiException("400","Email Id Required");
        }else if(!Pattern.matches(Constants.EMAIL_REGEX, request.getEmailId())) {
            throw new ApiException("400","Email id is invalid");
        }

        // Password must be non null
        if(request.getPassword() == null || request.getPassword() == "") {
            throw new ApiException("400","Password Required");
        } else if(!Pattern.matches(Constants.PASSWORD_REGEX, request.getPassword())) {
            throw new ApiException("400", "Password is not strong");
        }

        // Mobile No must be non null and contains digits only with limit 10
        if(request.getMobileNo() == null) {
            throw new ApiException("400","Mobile No Required");
        }else if(request.getMobileNo().length() != 10 || !Pattern.matches(Constants.DECIMAL_REGEX, request.getMobileNo())) {
            throw new ApiException("400","Mobile No is invalid");
        }

        // Checking Validation for vehicle objects
        if(request.getVehicles() == null || request.getVehicles().size() < 1) {
            throw new ApiException("400","No Vehicle provided");
        }
        for(VehicleAddRequest vehicleAddDTO: request.getVehicles()) {
            VehicleValidator.validate(vehicleAddDTO);
        }
    }

    public static void validate(OperatorUpdateRequest request) {
        if(request.getMobileNo() != null && (request.getMobileNo().length() != 10 || !Pattern.matches(Constants.DECIMAL_REGEX, request.getMobileNo()))) {
            throw new ApiException("400","Mobile No is invalid");
        }
    }
}
