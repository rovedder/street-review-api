package br.com.street.reviews.service.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import com.itextpdf.text.Document;

import br.com.street.reviews.model.Address;
import br.com.street.reviews.model.Answer;
import br.com.street.reviews.model.Review;

public class TestReviewPDF {

	public static void main(String[] args) throws IOException, ParserConfigurationException {
		Document pdf = new Document();
		
		Review review = new Review();
		review.setId((long) 1);
		review.setEmail("blablabla");
		
		Address address = new Address();
		address.setCity("santa maria");
		address.setDistrict("t neves");
		address.setPostalCode("97032-120");
		address.setState("rs");
		address.setStreet("dario prates rodrigues");
		
		review.setAddress(address);
		
		List<Answer> answers = new ArrayList<Answer>();
		for(int i = 0; i < 10; i++) {
			Answer answer = new Answer();
			answer.setAnswer("resposta " + i);
			answer.setCode((long) i + 1);
			
			answers.add(answer);
		}
		
//		ReviewPDF.generatePDF(answers, review);

	}

}
