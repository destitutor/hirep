package net.hexabrain.hireo.web.job.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import net.hexabrain.hireo.web.account.domain.Account;
import net.hexabrain.hireo.web.account.domain.AccountType;
import net.hexabrain.hireo.web.account.domain.Employer;
import net.hexabrain.hireo.web.account.domain.Freelancer;
import net.hexabrain.hireo.web.account.repository.AccountRepository;
import net.hexabrain.hireo.web.common.exception.company.UnauthorizedException;
import net.hexabrain.hireo.web.job.domain.Job;
import net.hexabrain.hireo.web.job.dto.JobPostRequest;
import net.hexabrain.hireo.web.job.dto.mapper.JobDetailsMapper;
import net.hexabrain.hireo.web.job.dto.mapper.JobInfoMapper;
import net.hexabrain.hireo.web.job.dto.mapper.JobPostRequestMapper;
import net.hexabrain.hireo.web.job.repository.JobRepository;

@ExtendWith(MockitoExtension.class)
class JobServiceTest {
	@Mock
	JobInfoMapper jobInfoMapper;

	@Mock
	JobPostRequestMapper jobPostRequestMapper;

	@Mock
	JobDetailsMapper jobDetailsMapper;

	@Mock
	JobRepository jobRepository;

	@Mock
	AccountRepository accountRepository;

	JobService service;

	User user;
	JobPostRequest postRequest;

	@BeforeEach
	void setUp() {
		service = spy(new JobService(jobRepository, accountRepository,
			jobInfoMapper, jobDetailsMapper, jobPostRequestMapper));

		user = new User("USER", "", Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
		postRequest = JobPostRequest.builder()
			.build();
	}

	@Test
	@DisplayName("채용자가 아닌 회원이 구인 등록을 시도하면 예외를 던짐")
	void throwsExceptionWhenNotEmployer() {
		// given
		Account poster = Freelancer.builder()
			.type(AccountType.FREELANCER)
			.build();

		when(accountRepository.findByEmailOrThrow(user.getUsername())).thenReturn(poster);

		// when, then
		assertThatThrownBy(() -> service.post(user, postRequest))
				.isInstanceOf(UnauthorizedException.class);
	}

	@Test
	@DisplayName("채용자가 구인 등록을 시도하면 정상적으로 처리됨")
	void postJob() {
		// given
		Account poster = Employer.builder()
			.type(AccountType.EMPLOYER)
			.build();

		when(accountRepository.findByEmailOrThrow(user.getUsername())).thenReturn(poster);
		when(jobPostRequestMapper.toEntity(postRequest)).thenReturn(mock(Job.class));
		when(jobRepository.save(any(Job.class))).thenReturn(mock(Job.class));

		// when
		service.post(user, postRequest);

		// then
		verify(jobRepository).save(any(Job.class));
	}
}