package br.com.street.reviews.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Question {
	
	private long code;
	private String question;

}
