package br.com.luizmariodev.wizs.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.luizmariodev.wizs.domain.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
	
	@Query(" FROM Customer c WHERE c.id <> :customerId AND c.documentNumber = :documentNumber")
	Optional<Customer> findCutomerByDocumentNumber(@Param("customerId") Long customerId, @Param("documentNumber") String documentNumber);

	@Query(" FROM Customer c WHERE c.documentNumber = :documentNumber")
	Optional<Customer> findCutomerByDocumentNumber(@Param("documentNumber") String documentNumber);	
}
