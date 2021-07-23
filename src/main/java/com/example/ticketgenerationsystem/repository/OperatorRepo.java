package com.example.ticketgenerationsystem.repository;

import com.example.ticketgenerationsystem.entity.Operator;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperatorRepo extends PagingAndSortingRepository<Operator, Integer> {
    List<Operator> findByMobileNo(String mobileNo);
}
