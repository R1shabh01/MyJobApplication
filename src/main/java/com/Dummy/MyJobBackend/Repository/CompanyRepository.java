package com.Dummy.MyJobBackend.Repository;

import com.Dummy.MyJobBackend.Entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {
}
