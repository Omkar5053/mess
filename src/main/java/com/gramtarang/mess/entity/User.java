package com.gramtarang.mess.entity;

import com.gramtarang.mess.common.UserStatus;
import com.gramtarang.mess.enums.RoleType;
import com.gramtarang.mess.enums.UserType;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    private String userName;
    @Enumerated(value = EnumType.ORDINAL)
    private UserStatus isActive;
    private String email;
    private String firstName;
    private String phoneNo;
    private String lastName;
    private Date createdAt;
    private Date updatedAt;
    private String password;
    @Enumerated(value = EnumType.ORDINAL)
    private UserType userType;

    @Enumerated(value = EnumType.ORDINAL)
    private RoleType roleType;

    @ManyToOne
    @JoinColumn(name = "hostel_id")
    private Hostel hostel;
//    @ManyToOne
//    @JoinColumn(name = "role_id")
//    private Role role;


    public User() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public UserStatus getIsActive() {
        return isActive;
    }

    public void setIsActive(UserStatus isActive) {
        this.isActive = isActive;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() { return phoneNo; }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }
    //    public Role getRole() {
//        return role;
//    }
//
//    public void setRole(Role role) {
//        this.role = role;
//    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Hostel getHostel() {
        return hostel;
    }

    public void setHostel(Hostel hostel) {
        this.hostel = hostel;
    }
}
