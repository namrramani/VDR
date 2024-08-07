package com.telecom.servicesofI;

import java.util.List;

import com.telecom.models.tables.MSTledger;


public interface MSTledgerService {

	MSTledger save(MSTledger mstledger);
	MSTledger findById(String id);
	boolean delete(MSTledger mstledger);
	List<MSTledger> findAll();
	MSTledger updateById(MSTledger mstledger);
	MSTledger getledgerByCustomerId(String customerId);
}
