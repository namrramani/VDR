package com.telecom.services.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telecom.models.tables.Admin;
import com.telecom.models.tables.Customer;
import com.telecom.repository.sqlrepo.AdminRepository;
import com.telecom.repository.sqlrepo.CustomerRepository;
import com.telecom.servicesofI.AdminService;

@Service
public class AdminServiceImpl implements AdminService{

private static final Logger logger = LogManager.getLogger(AdminServiceImpl.class);
	
	@Autowired
	AdminRepository adminRepository;
	
	@Override
	public Admin save(Admin admin) {
		try {
			return adminRepository.save(admin);
		}catch(Exception e) {
			logger.info("Exception at CustomerServiceImpl : save "+ e);
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Admin findById(String id) {
		try {
			return adminRepository.findById(id).get();
		}catch(Exception e) {
			logger.info("Exception at CustomerServiceImpl : save "+ e);
		}
		return null;
	}

	@Override
	public boolean deleteUser(String adminID) {
		try {
			adminRepository.moveToArchive(adminID);
			return true;
		}catch(Exception e) {
			logger.info("Exception at CustomerServiceImpl : delete "+ e);
		}
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Admin> findAll() {
		try {
			return adminRepository.findAll();
		}catch(Exception e) {
			logger.info("Exception at CustomerServiceImpl : findAll "+ e);
		}
		return null;
	}

	@Override
	public Admin updateById(Admin admin) {
		try {
			return adminRepository.save(admin);
		}catch(Exception e) {
			logger.error("Exception at CustomerServiceImpl : updateById " +e);
		}
		return null;
	}

	@Override
	public Admin getUserByEmailPassword(String email, String password) {
		try {
			return adminRepository.getUserByEmailPassword(email, password);
		}catch (Exception e)
        {
       	 logger.error("Exception at  CustomerServiceImpl : getCustomerByEmailPassword "+e);
        }
        return null;
	}

	@Override
	public boolean isUserAvailable(String email) {
		try {
			Admin admin = adminRepository.isUserAvailable(email);
			if(admin != null) {
				return true;
			}
		}catch (Exception e)
        {
       	 logger.error("Exception at  CustomerServiceImpl : getCustomerByEmailPassword "+e);
        }
        return false;
	}

	@Override
	public List<Admin> getAllAdmin(){
		try {
			return adminRepository.getAllAdmin();
		}catch(Exception e) {
			logger.info("Exception at CustomerServiceImpl : findAll "+ e);
		}
		return null;
	}
	
}
