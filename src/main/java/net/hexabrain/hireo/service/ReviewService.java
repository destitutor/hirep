package net.hexabrain.hireo.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hexabrain.hireo.domain.Account;
import net.hexabrain.hireo.domain.Company;
import net.hexabrain.hireo.domain.Review;
import net.hexabrain.hireo.repository.AccountRepository;
import net.hexabrain.hireo.repository.CompanyRepository;
import net.hexabrain.hireo.repository.ReviewRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final CompanyRepository companyRepository;
    private final AccountRepository accountRepository;

    public Long post(Long companyId, Review review) {
        Optional<Company> company = companyRepository.findById(companyId);

        if (company.isPresent()) {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Account account = accountRepository.findByEmail(email);

            review.setAuthor(account);
            review.setCompany(company.get());
            review.setPostedAt(LocalDate.now());
            return reviewRepository.save(review).getId();
        } else {
            throw new RuntimeException("Company not found");
        }
    }

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }
}
