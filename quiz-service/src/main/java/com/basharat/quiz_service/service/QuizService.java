package com.basharat.quiz_service.service;


import com.basharat.quiz_service.dao.QuizDao;
import com.basharat.quiz_service.feign.QuizInterface;
import com.basharat.quiz_service.model.QuestionWrapper;
import com.basharat.quiz_service.model.Quiz;
import com.basharat.quiz_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    private QuizDao quizDao;
    @Autowired
    private QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String categoryName, int numQ, String title) {
        //call generate of Question Service using Rest Template-http://localhost:8080/question/generate
        List<Integer> questions=quizInterface.getQuestionsForQuiz(categoryName,numQ).getBody();
        Quiz quiz=new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Quiz quiz=quizDao.findById(id).get();
        List<Integer> questionIds=quiz.getQuestionIds();
        ResponseEntity <List<QuestionWrapper>> questions=quizInterface.getQuestionsFromId(questionIds);
        return questions;
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Optional<Quiz> quiz = quizDao.findById(id);
        int right = 0, i = 0;
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
