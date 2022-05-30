package net.hexabrain.hireo.service;

import lombok.RequiredArgsConstructor;
import net.hexabrain.hireo.domain.Company;
import net.hexabrain.hireo.repository.CompanyRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    public Company findOne(Long id) {
        return companyRepository.findById(id).get();
    }

    public boolean isExist(Long id) {
        return companyRepository.existsById(id);
    }
}