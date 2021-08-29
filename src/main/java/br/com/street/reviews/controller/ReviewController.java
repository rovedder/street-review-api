package br.com.street.reviews.controller;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.street.reviews.Application;
import br.com.street.reviews.controller.form.ReviewForm;
import br.com.street.reviews.model.Answer;
import br.com.street.reviews.model.Review;
import br.com.street.reviews.service.ReviewService;
import br.com.street.reviews.service.dto.ReviewDTO;

@RestController
@RequestMapping("/review")
public class ReviewController {
	
	private static Logger logger = LoggerFactory.getLogger(Application.class);
	
	@Autowired
	private ReviewService service;
	
	@PostMapping
	public ResponseEntity<ReviewDTO> register(@RequestBody @Valid ReviewForm form) {
		return service.register(form);
	}
	
	@GetMapping("/reviewList")
	public ResponseEntity<List<Review>> getAllByEmail (@RequestParam @Email String email) {
		return service.getAllByEmail(email);
	}
	
	@GetMapping("/export")
	public ResponseEntity<HttpStatus> exportById(@RequestParam @NotNull Long id) throws IOException, ParserConfigurationException, MessagingException {
		return service.exportById(id);
	}
	
}