package net.hexabrain.hireo.web.company.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import net.hexabrain.hireo.utils.HangulUtils;
import net.hexabrain.hireo.web.company.domain.Company;
import net.hexabrain.hireo.web.company.domain.QCompany;
import net.hexabrain.hireo.web.company.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {
    @PersistenceContext
    private EntityManager entityManager;
    private final CompanyRepository companyRepository;

    public Company findOne(Long id) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QCompany company = QCompany.company;
        return queryFactory
                .selectFrom(company)
                .join(company.reviews).fetchJoin()
                .where(company.id.eq(id))
                .fetchOne();
    }

    public boolean isExist(Long id) {
        return companyRepository.existsById(id);
    }

    public long count() {
            return companyRepository.count();
    }

    public List<Company> list(char firstConsonant) {
        firstConsonant = normalize(firstConsonant);

        char startLetter = HangulUtils.getCompleteStartLetterFrom(firstConsonant);
        char endLetter = HangulUtils.getCompleteEndLetterFrom(firstConsonant);

        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QCompany company = QCompany.company;
        return queryFactory
                .selectFrom(company)
                .where(company.name.substring(0, 1).between(String.valueOf(startLetter), String.valueOf(endLetter)))
                .fetch();
    }

    private char normalize(char firstConsonant) {
        if (HangulUtils.isDoubleConsonant(firstConsonant)) {
            firstConsonant = (char)(firstConsonant - 1);
        }
        return firstConsonant;
    }
}