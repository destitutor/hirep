package net.hexabrain.hireo.web.company.repository;

import net.hexabrain.hireo.web.company.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
