package com.telecom.repository.sqlrepo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.telecom.models.tables.MSTledger;

public interface MSTledgerRepository extends JpaRepository<MSTledger, String>{

	@Query(value = "SELECT * FROM mst_ledger_details Where customerID_mst_ledger_details=?1", nativeQuery = true)
	public MSTledger getledgerByCustomerId(String customerId);

}

