package br.com.street.reviews.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.parsers.ParserConfigurationException;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;

import br.com.street.reviews.Application;
import br.com.street.reviews.controller.form.ReviewForm;
import br.com.street.reviews.model.Answer;
import br.com.street.reviews.model.Review;
import br.com.street.reviews.repository.AnswerRepository;
import br.com.street.reviews.repository.ReviewRepository;
import br.com.street.reviews.service.dto.ReviewDTO;
import br.com.street.reviews.service.helper.Mail;
import br.com.street.reviews.service.helper.ReviewPDF;

@Service
public class ReviewService {

	private static Logger logger = LoggerFactory.getLogger(Application.class);
	
	@Autowired
	public ReviewRepository reviewRepository;
	
	@Autowired
	public AnswerRepository answerRepository;
	
	@Autowired
	private SendEmailService emailService;
	
	public ResponseEntity<ReviewDTO> register(ReviewForm form) {
		Review newReview = new Review();
		newReview = form.converter();
		
		try {
			reviewRepository.save(newReview);
			
		} catch(Exception e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
		try {
			List<Answer> newAnswers = new ArrayList<Answer>();
			newAnswers = form.getAnswers();
			
			for(int i = 0; i < newAnswers.size(); i++) 
				newAnswers.get(i).setReviewId(newReview.getId());

			answerRepository.saveAll(newAnswers);

			return ResponseEntity.status(HttpStatus.OK).body(new ReviewDTO(newReview));
		} catch(Exception e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	public ResponseEntity<List<ReviewDTO>> getAllByEmail(String email) {
		try {
			List<Review> listReview = reviewRepository.findAllByEmail(email);
			List<ReviewDTO> listDTO = new ArrayList<ReviewDTO>();
			
			for(Review review: listReview) {
				ReviewDTO newDto = new ReviewDTO(review);
				
				listDTO.add(newDto);
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(listDTO);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	public ResponseEntity<HttpStatus> exportById(Long id) throws IOException, ParserConfigurationException, MessagingException {
		List<Answer> answers = new ArrayList<Answer>();
		Review review = new Review();
		
		try {
			answers = answerRepository.findAllByReviewId(id);
			review = reviewRepository.findById(id).get();
		} 
		catch(Exception e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
		String filePath = "./PDF/review-" + review.getId() + ".pdf";
		
		ReviewPDF pdf = new ReviewPDF(); 
		pdf.generatePDF(answers, review, filePath);
		
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
}
