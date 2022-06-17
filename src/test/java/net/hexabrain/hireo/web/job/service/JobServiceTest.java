package net.hexabrain.hireo.web.job.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
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
import net.hexabrain.hireo.web.job.domain.Tag;
import net.hexabrain.hireo.web.job.dto.JobPostRequestDto;
import net.hexabrain.hireo.web.job.dto.mapper.JobMapper;
import net.hexabrain.hireo.web.job.dto.mapper.JobPostRequestMapper;
import net.hexabrain.hireo.web.job.repository.JobRepository;
import net.hexabrain.hireo.web.job.repository.TagRepository;

@ExtendWith(MockitoExtension.class)
class JobServiceTest {
	@Mock
	JobPostRequestMapper jobPostRequestMapper;

	@Mock
	JobMapper jobMapper;

	@Mock
	JobRepository jobRepository;

	@Mock
	AccountRepository accountRepository;

	@Mock
	TagRepository tagRepository;

	JobService service;

	User user;
	JobPostRequestDto postRequest;

	@BeforeEach
	void setUp() {
		service = spy(new JobService(tagRepository, jobRepository, accountRepository,
			jobMapper, jobPostRequestMapper));

		user = new User("USER", "", Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
		postRequest = JobPostRequestDto.builder()
			.tags(new String[] { "tag1", "tag2" })
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
		int tagLength = postRequest.getTags().length;
		verify(jobRepository).save(any(Job.class));
		verify(tagRepository, times(tagLength)).save(any(Tag.class));
	}
}