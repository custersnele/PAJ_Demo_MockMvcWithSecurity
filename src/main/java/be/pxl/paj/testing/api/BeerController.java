package be.pxl.paj.testing.api;

import be.pxl.paj.testing.exception.InvalidColorException;
import be.pxl.paj.testing.service.BeerExpert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class BeerController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BeerController.class);

	private static final List<String> ALLOWED_COLORS = Arrays.asList("amber", "light", "dark", "brown");
	private final BeerExpert beerExpert;

	public BeerController(BeerExpert beerExpert) {
		this.beerExpert = beerExpert;
	}

	@GetMapping("/beers")
	public ResponseEntity<List<String>> getRecommendations(@AuthenticationPrincipal final User user, @RequestParam("color") String color) {
		System.out.println("Lookup beers for " + user.getUsername());
		if (!ALLOWED_COLORS.contains(color)) {
			throw new InvalidColorException();
		}
		List<String> brands = beerExpert.getBrands(color);
		return ResponseEntity.ok(brands);
	}

}
