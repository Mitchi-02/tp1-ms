package com.esisba.tp1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.transaction.annotation.Transactional;

import com.esisba.tp1.entity.Vm;


@RepositoryRestController
public interface VmRepository extends JpaRepository<Vm, Long> {

    @Modifying
    @Transactional
    @Query(value = "Delete FROM Vm vm WHERE vm.id = :id and (vm.primary_server.id = :serverId or vm.backup_server.id = :serverId)")
    void deleteByIdAndServerId(@Param("id") Long id, @Param("serverId") Long serverId);
}