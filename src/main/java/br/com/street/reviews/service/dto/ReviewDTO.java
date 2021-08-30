package br.com.street.reviews.service.dto;

import java.util.List;

import br.com.street.reviews.model.Address;
import br.com.street.reviews.model.Answer;
import br.com.street.reviews.model.Review;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ReviewDTO {
	
	private long id;
	private String email;
	private Address address;
	
	public ReviewDTO(Review review) {
		this.setId(review.getId());
		this.setEmail(review.getEmail());
		this.setAddress(review.getAddress());
	}
	
}
