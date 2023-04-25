package com.gramtarang.mess.repository;

import com.gramtarang.mess.entity.Mess;
import com.gramtarang.mess.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MessRepository extends JpaRepository<Mess, Integer> {

    @Query("from Mess where user = :user")
    Mess findByUser(User user);

}
