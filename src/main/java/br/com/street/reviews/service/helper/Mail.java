package br.com.street.reviews.service.helper;

import java.io.File;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Mail {
	
	private String email;
    private String subject;
    private String message;
    private File document;

}
