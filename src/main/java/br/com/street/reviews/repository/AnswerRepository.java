package br.com.street.reviews.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.street.reviews.model.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
	
	List<Answer> findAllByReviewId(Long id);

}
