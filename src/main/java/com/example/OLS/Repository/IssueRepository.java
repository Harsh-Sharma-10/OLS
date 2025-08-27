package com.example.OLS.Repository;

import com.example.OLS.Model.IssueTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueRepository extends JpaRepository<IssueTransaction,String> {

}
