package com.example.ticketgenerationsystem.repository;

import com.example.ticketgenerationsystem.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends PagingAndSortingRepository<User,Integer> {
    List<User> findAllByUserType(int role);
}
