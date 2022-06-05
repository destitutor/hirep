package net.hexabrain.hireo.web.review.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hexabrain.hireo.web.account.domain.Account;
import net.hexabrain.hireo.web.company.domain.Company;
import net.hexabrain.hireo.web.review.domain.Review;
import net.hexabrain.hireo.web.account.repository.AccountRepository;
import net.hexabrain.hireo.web.company.repository.CompanyRepository;
import net.hexabrain.hireo.web.review.repository.ReviewRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
            UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Account account = accountRepository.findByEmail(user.getUsername());

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
