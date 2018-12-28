package com.locator.locator.repo;

import com.locator.locator.common.Locator_Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Locator_Repo extends JpaRepository<Locator_Model, Integer> {
}
