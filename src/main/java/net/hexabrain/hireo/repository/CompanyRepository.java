package net.hexabrain.hireo.repository;

import net.hexabrain.hireo.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
