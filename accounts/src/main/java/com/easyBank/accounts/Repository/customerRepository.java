package com.easyBank.accounts.Repository;

import com.easyBank.accounts.Entity.customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface customerRepository extends JpaRepository<customer,Long> {

     Optional<customer> findBymobileNumber(String mobileNumber);
}
