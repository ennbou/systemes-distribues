package com.ennbou.facturesconsumer.dao;
import com.ennbou.facturesconsumer.entities.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface  FactureRepository extends JpaRepository<Facture, Long>{
}
