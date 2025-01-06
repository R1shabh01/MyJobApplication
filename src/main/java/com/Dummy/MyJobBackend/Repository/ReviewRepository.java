package com.Dummy.MyJobBackend.Repository;

import com.Dummy.MyJobBackend.Entity.Company;
import com.Dummy.MyJobBackend.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    @Query("SELECT r FROM Review r WHERE r.company.id =:id")
    List<Review> findByCompanyId(@Param("id")Long id);
}
