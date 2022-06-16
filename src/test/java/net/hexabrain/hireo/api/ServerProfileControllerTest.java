package net.hexabrain.hireo.api;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

class ServerProfileControllerTest {

	@Test
	@DisplayName("production 프로필을 조회함")
	void testProductionProfile() {
		String expectedProfile = "production1";
		MockEnvironment env = new MockEnvironment();

		env.addActiveProfile(expectedProfile);
		env.addActiveProfile("oauth");
		env.addActiveProfile("db");

		ServerProfileController controller = new ServerProfileController(env);
		String profile = controller.profile();

		assertThat(profile).isEqualTo(expectedProfile);
	}

	@Test
	@DisplayName("production 프로필이 없을 경우 default를 반환함")
	void testDefaultProfileWhenNoProductionProfile() {
		String expectedProfile = "oauth";
		MockEnvironment env = new MockEnvironment();

		env.addActiveProfile(expectedProfile);
		env.addActiveProfile("db");

		ServerProfileController controller = new ServerProfileController(env);
		String profile = controller.profile();

		assertThat(profile).isEqualTo(expectedProfile);
	}

	@Test
	@DisplayName("활성 프로필이 없으면 default를 반환함")
	void testDefaultProfileWhenNoActiveProfile() {
		String expectedProfile = "default";
		MockEnvironment env = new MockEnvironment();

		ServerProfileController controller = new ServerProfileController(env);
		String profile = controller.profile();

		assertThat(profile).isEqualTo(expectedProfile);
	}
}