package com.ennbou.stream1.dao;

import com.ennbou.stream1.entities.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface ContractRepository extends JpaRepository<Contract, Long> {
}
