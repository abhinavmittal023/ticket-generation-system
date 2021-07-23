package com.example.ticketgenerationsystem.validator;

import com.example.ticketgenerationsystem.constant.Constants;
import com.example.ticketgenerationsystem.exception.ApiException;
import com.example.ticketgenerationsystem.request.ExecutiveSignupRequest;
import com.example.ticketgenerationsystem.request.ExecutiveUpdateRequest;
import com.example.ticketgenerationsystem.request.PasswordUpdateRequest;

import java.util.regex.Pattern;

public class ExecutiveValidator {
    public static void validate(ExecutiveSignupRequest request) {
        // First Name must be non null and contain alphabets only
        if(request.getFirstName() == null || request.getFirstName() == "") {
            throw new ApiException("400","First Name Required");
        }else if(!Pattern.matches(Constants.ALPHABET_REGEX, request.getFirstName())) {
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
            throw new ApiException("400","Password is not strong");
        }

        // Mobile No must be non null and contains digits only with limit 10
        if(request.getMobileNo() == null) {
            throw new ApiException("400","Mobile No Required");
        }else if(request.getMobileNo().length() != 10 || !Pattern.matches(Constants.DECIMAL_REGEX, request.getMobileNo())) {
            throw new ApiException("400","Mobile No is invalid");
        }

        if(request.getLocation() == null || request.getLocation().length != 2) {
            throw new ApiException("400","Please provide correct location parameters");
        }
    }

    public static void validate(ExecutiveUpdateRequest request) {
        if(request.getExecutiveId() == null || request.getExecutiveId().equals(0)) {
            throw new ApiException("400","User Id Required");
        }
        if(request.getNewLocation() != null && request.getNewLocation().length != 2) {
            throw new ApiException("400","Please provide correct location parameters");
        }
        if(request.getNewMobileNo() != null && (request.getNewMobileNo().length() != 10 || !Pattern.matches(Constants.DECIMAL_REGEX, request.getNewMobileNo()))) {
            throw new ApiException("400","Mobile No is invalid");
        }
        if(request.getNewPassword() != null && !Pattern.matches(Constants.PASSWORD_REGEX, request.getNewPassword())) {
            throw new ApiException("400", "Password is not strong");
        }
    }

    public static void validate(PasswordUpdateRequest request) {
        if(request.getOldPassword() == null || request.getNewPassword() == null) {
            throw new ApiException("400", Constants.INVALID_REQUEST_PARAMETERS);
        }
        if(!Pattern.matches(Constants.PASSWORD_REGEX, request.getNewPassword())) {
            throw new ApiException("400", "New Password is not strong enough");
        }
    }
}
