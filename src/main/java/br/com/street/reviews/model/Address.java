package br.com.street.reviews.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String street;
	
	@NotBlank
	private String city;
	
	@NotBlank
	private String state;
	
	@NotBlank
	private String district;
	
	@NotBlank
	private String postalCode;
	
	@OneToOne
	@JsonBackReference
	private Review review;
	
	public String getFullAddress() {
		String street = "Rua: " + this.street + "\n";
		String city = "Cidade: " + this.city + "\n";
		String state = "Estado: " + this.state + "\n";
		String district = "Bairro: " + this.district + "\n";
		String postalCode = "CEP: " + this.postalCode + "\n";
		return street + city + state + district + postalCode;
	}
	
}
