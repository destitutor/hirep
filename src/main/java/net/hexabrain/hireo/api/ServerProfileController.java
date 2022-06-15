package net.hexabrain.hireo.api;

import java.util.Arrays;
import java.util.List;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ServerProfileController {
	private final Environment env;

	@GetMapping("/profile")
	public String profile() {
		List<String> profiles = Arrays.asList(env.getActiveProfiles());
		List<String> realProfiles = Arrays.asList("production1", "production2");
		String defaultProfile = profiles.isEmpty() ? "default" : profiles.get(0);
		return profiles.stream()
			.filter(realProfiles::contains)
			.findAny()
			.orElse(defaultProfile);
	}
}
