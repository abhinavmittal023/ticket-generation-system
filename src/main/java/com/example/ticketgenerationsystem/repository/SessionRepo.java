package com.example.ticketgenerationsystem.repository;

import com.example.ticketgenerationsystem.entity.Session;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SessionRepo extends PagingAndSortingRepository<Session, Integer> {
}
