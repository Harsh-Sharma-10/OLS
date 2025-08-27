package com.example.OLS.Repository;

import com.example.OLS.Model.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Repouser extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
