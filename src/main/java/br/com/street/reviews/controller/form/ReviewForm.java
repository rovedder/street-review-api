package br.com.street.reviews.controller.form;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.street.reviews.model.Address;
import br.com.street.reviews.model.Answer;
import br.com.street.reviews.model.Review;
import lombok.Data;

@Data
public class ReviewForm {
	@NotBlank
	private String email;
	
	@NotNull
	private Address address;
	
	@NotNull
	private List<Answer> answers;
	
	public ReviewForm(String email, Address address, List<Answer> answers) {
		this.email = email;
		this.address = address;
		this.answers = answers;
	}

	public Review converter() {
		return new Review(this.email, this.address);
	}
}
