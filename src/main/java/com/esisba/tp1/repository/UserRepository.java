package com.esisba.tp1.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import com.esisba.tp1.entity.User;


@RepositoryRestController
public interface UserRepository extends JpaRepository<User, Long> {

    Collection<User> findByEmailEndingWith(String end);

    @Query("SELECT count(vm) FROM Vm vm WHERE vm.user.id = :id")
    Long getVmCount(@Param("id") Long id);
}