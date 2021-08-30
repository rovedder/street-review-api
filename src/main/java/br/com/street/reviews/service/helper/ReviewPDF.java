package br.com.street.reviews.service.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import br.com.street.reviews.Application;
import br.com.street.reviews.model.Answer;
import br.com.street.reviews.model.Question;
import br.com.street.reviews.model.Review;

public class ReviewPDF {
	
	private static Logger logger = LoggerFactory.getLogger(Application.class);
	
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
	            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    
	
	public  void generatePDF(List<Answer> answers, Review review, String filePath) throws IOException, ParserConfigurationException {
		Document pdf = new Document();
		
		try {
			PdfWriter.getInstance(pdf, new FileOutputStream(filePath));
			pdf.open();

			addHeader(pdf, review);
			addContent(pdf, answers);
			
			pdf.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		} catch (DocumentException e) {
			e.printStackTrace();
			return;
		}
	}

	private static void addHeader(Document pdf, Review review) throws DocumentException{
		Paragraph preface = new Paragraph();
		
        addEmptyLine(preface, 1);
        
        preface.add(new Paragraph("Street Review", catFont));
        
        addEmptyLine(preface, 1);
        
        preface.add(new Paragraph("Endereco cadastrado: \n" + review.getAddress().getFullAddress(), catFont));
        
        addEmptyLine(preface, 2);
        
        pdf.add(preface);
	}
	
	private static void addContent(Document pdf, List<Answer> answers) throws IOException, ParserConfigurationException, DocumentException{
		Paragraph preface = new Paragraph();
		
		List<Question> questions = parseQuestionsXML();
		
		for(Answer answer : answers) {
			
			for(Question question : questions) {
				if(question.getCode() == answer.getCode())
					preface.add(new Paragraph(question.getQuestion(), subFont));
			}
			
			preface.add(new Paragraph(answer.getAnswer(), smallBold));
			
			addEmptyLine(preface, 1);
		}
		
		pdf.add(preface);
		
	}
	
	private static List<Question> parseQuestionsXML() throws ParserConfigurationException, IOException {
		List<Question> questions = new ArrayList<Question>();
		Question question = null;
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		org.w3c.dom.Document document;
		try {
			document = builder.parse(new File("questions.xml"));
			document.getDocumentElement().normalize();
			org.w3c.dom.NodeList nList = document.getElementsByTagName("question");
			
			for(int i = 0; i < nList.getLength(); i++) {
				Node node = nList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) node;
					
					question = new Question();
					question.setCode(Long.parseLong(eElement.getAttribute("id")));
					question.setQuestion(eElement.getElementsByTagName("text").item(0).getTextContent());
					
					questions.add(question);
				}
			}
			
			return questions;
		} catch (SAXException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) 
            paragraph.add(new Paragraph(" "));
    }
}
