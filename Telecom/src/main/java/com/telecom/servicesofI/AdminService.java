package com.telecom.servicesofI;

import java.util.List;

import com.telecom.models.tables.Admin;


public interface AdminService {

	Admin save(Admin admin);
	Admin findById(String id);
	boolean deleteUser(String adminID);
	List<Admin> findAll();
	Admin updateById(Admin admin);
	Admin getUserByEmailPassword(String email,String password);
	boolean isUserAvailable(String email);
	List<Admin> getAllAdmin();
}
