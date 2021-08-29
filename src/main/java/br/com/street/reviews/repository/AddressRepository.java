package br.com.street.reviews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.street.reviews.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
