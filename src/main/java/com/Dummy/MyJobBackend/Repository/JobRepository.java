package com.Dummy.MyJobBackend.Repository;


import com.Dummy.MyJobBackend.Entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job,Long> {
}
