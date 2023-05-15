package com.gramtarang.mess.repository;

import com.gramtarang.mess.entity.Mess;
import com.gramtarang.mess.entity.MessUser;
import com.gramtarang.mess.enums.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface MessUserRepository extends JpaRepository<MessUser, Integer> {

//    @Query("from MessUser as m where m.user.userType = :userType")
    List<MessUser> findByUserUserType(UserType userType);
}
