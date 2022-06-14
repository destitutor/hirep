package net.hexabrain.hireo.web.account.controller;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import net.hexabrain.hireo.web.account.domain.Account;
import net.hexabrain.hireo.web.account.dto.SignUpDto;
import net.hexabrain.hireo.web.account.service.AccountService;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {
	private MockMvc mockMvc;

	@MockBean
	private AccountService accountService;

	@BeforeEach
	void setUp(WebApplicationContext webApplicationContext) {
		this.mockMvc = MockMvcBuilders
			.webAppContextSetup(webApplicationContext)
			.apply(springSecurity())
			.build();
	}

	@Test
	@DisplayName("로그인 페이지로 이동함")
	void forwardToLogin() throws Exception {
		mockMvc.perform(get("/accounts/login"))
			.andExpect(view().name("login"))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("회원가입 페이지로 이동")
	void forwardToSignUp() throws Exception {
		mockMvc.perform(get("/accounts/new"))
			.andExpect(model().attributeExists("account"))
			.andExpect(model().attribute("account", Matchers.instanceOf(SignUpDto.class)))
			.andExpect(view().name("new"))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("회원가입 성공")
	void signUpSuccess() throws Exception {
		when(accountService.save(any(Account.class))).thenReturn(mock(Account.class));

		mockMvc.perform(post("/accounts/new")
			.param("email", "test@test.com")
			.param("password", "test1234")
			.param("passwordConfirm", "test1234")
			.param("type", "FREELANCER").with(csrf()))
			.andExpect(redirectedUrl("/account/login"));

		verify(accountService).save(any(Account.class));
	}

	@Test
	@DisplayName("회원가입 시 데이터베이스 저장 실패")
	void signUpFail() throws Exception {
		when(accountService.save(any(Account.class))).thenThrow(DataIntegrityViolationException.class);

		mockMvc.perform(post("/accounts/new")
				.param("email", "test@test.com")
				.param("password", "test1234")
				.param("passwordConfirm", "test1234")
				.param("type", "FREELANCER").with(csrf()))
			.andExpect(view().name("new"));

		verify(accountService).save(any(Account.class));
	}
}