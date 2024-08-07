package com.telecom.repository.sqlrepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.telecom.models.tables.Admin;
import com.telecom.models.tables.Customer;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
	
	@Query(value = "SELECT * FROM admin_details Where email_admin_details=?1 and password_admin_details=?2", nativeQuery = true)
	public Admin getUserByEmailPassword(String email,String password);
	
	@Query(value = "SELECT * FROM admin_details Where email_admin_details=?1", nativeQuery = true)
	public Admin isUserAvailable(String email);
	
	@Modifying
	@Transactional
	@Query(value = "Update admin_details set isArchive_admin_details=1 Where ID_admin_details=?1", nativeQuery = true)
	public void moveToArchive(String adminID);
	
	@Query(value = "SELECT * FROM admin_details Where isArchive_admin_details=0", nativeQuery = true)
	public List<Admin> getAllAdmin();
}
