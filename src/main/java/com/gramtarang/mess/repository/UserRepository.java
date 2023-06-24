package com.gramtarang.mess.repository;

import com.gramtarang.mess.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


    @Query("from User where email = :email and password = :password")
    User findUserByEmailAddressAndPassword(String email, String password);

    @Query(value= "SELECT * from user u where u.hostel_id = :hostel_id", nativeQuery = true)
    List<User> findUsersByHostel_HostelId(Integer hostel_id);
    @Query(value= "select * from user where user_name = :regNo", nativeQuery = true)
    User findUserByUserName(String regNo);

}
