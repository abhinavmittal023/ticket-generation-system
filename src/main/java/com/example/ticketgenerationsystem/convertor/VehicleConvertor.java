package com.example.ticketgenerationsystem.convertor;

import com.example.ticketgenerationsystem.request.VehicleAddRequest;
import com.example.ticketgenerationsystem.entity.Operator;
import com.example.ticketgenerationsystem.entity.Vehicle;

public class VehicleConvertor {
    public static Vehicle convert(VehicleAddRequest vehicleAddDTO, Operator operatorReference) {
        Vehicle vehicle = new Vehicle();

        vehicle.setRegNo(vehicleAddDTO.getRegNo());
        vehicle.setModel(vehicleAddDTO.getModel());
        vehicle.setOperator(operatorReference);

        return vehicle;
    }
}
