package com.quizly.quizs.controller;

import com.quizly.quizs.constants.SecurityConstants;
import com.quizly.quizs.dtos.*;
import com.quizly.quizs.models.*;
import com.quizly.quizs.services.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class QuizController {
    @Autowired
    private QuizService quizService;
    @Autowired
    private ParticipantService participantService;
    @Autowired
    private ResultService resultService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;
    private Map<String, List<OwnerQuizDto>> quizCache = new ConcurrentHashMap<>();

    private final RestTemplate restTemplate;
    @PostMapping("/{quizId}")
    public ResponseEntity<?> addParticipant(
            @PathVariable Long quizId,
            @RequestBody ResultDto resultDto
    ){
        try{
        Optional<Quiz> quizOptional = quizService.getQuizById(quizId);
        if (quizOptional.isEmpty()){
            return new ResponseEntity<>("Quiz not found", HttpStatus.NOT_FOUND);
        }
        Quiz quiz = quizOptional.get();
        Result result = DtoConv.resultDtoToResult(resultDto);
        result.setQuiz(quiz);
        resultService.saveResult(result);
        return new ResponseEntity<>("Result added to the quiz", HttpStatus.CREATED);
        } catch (Exception e){
            System.out.println("Error adding result to the quiz" + e.getMessage());
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/statistics/{user}")
    public ResponseEntity<UserQuizsDto> getUserStatistics(@PathVariable(name = "user") String user){
        try {
            Long createdQuizzesCount = quizService.countQuizzesByOwner(user);
            Long participatedQuizzesCount = participantService.countParticipationByUsername(user);
            var stats = UserQuizsDto.builder().quizsCreated(createdQuizzesCount).quizsTaken(participatedQuizzesCount).build();
            return ResponseEntity.ok(stats);
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/quizzes/{owner}")
    public ResponseEntity<QuizListDto> getQuizByOwner(
            @PathVariable String owner,
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "page",defaultValue = "5") int size
    ) {
        System.out.println(owner);
        try {
            List<Quiz> quizzes = quizService.getQuizByOwner(owner);
            if (quizzes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<QuizDto> quizDtos = quizzes.stream().map(quiz -> {
                QuizDto quizDto = DtoConv.quizToQuizDto(quiz);

                // Directly use the returned QuestionDto list from the service
                List<QuestionDto> questionDtos = questionService.getQuestionByQuiz(quiz);
                quizDto.setQuestions(questionDtos);

                // Manually set the Results in QuizDto
                List<ResultDto> resultDtos = resultService.getResultByQuiz(quiz);
//                quizDto.setResults(resultDtos);

                return quizDto;
            }).collect(Collectors.toList());

            var finalDto = QuizListDto.builder()
                    .quizs(quizDtos)
                    .page(1L)
                    .isLast(false)
                    .build();

            return new ResponseEntity<>(finalDto, HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/followers/{username}")
    public ResponseEntity<?> getFollowers(@PathVariable String username) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(SecurityConstants.AUTHORIZED_USER_HEADER, username);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            String url = "lb://users-service/followed";
            ResponseEntity<List<String>> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<String>>() {});

            List<String> followers = response.getBody();
            if (followers == null || followers.isEmpty()) {
                return new ResponseEntity<>("No followers found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(followers, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error retrieving followers: " + e.getMessage());
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/followers-quizzes/{username}")
    public ResponseEntity<QuizListDto> getQuizzesOfFollowers(
            @PathVariable String username,
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = "10") int size) {
        System.out.println("Getting quizzes of followers for: " + username);
        try {
            List<OwnerQuizDto> allQuizzes = quizCache.get(username);
            if (allQuizzes == null) {
                allQuizzes = fetchQuizzes(username);
                quizCache.put(username, allQuizzes);
            }

            if (allQuizzes.isEmpty()) {
                System.out.println("No quizzes found for followers of: " + username);
                return new ResponseEntity<>(QuizListDto.builder().quizs(new ArrayList<>())
                        .page((long)page)
                        .isLast(true)
                        .build(), HttpStatus.OK);
            }

            int totalQuizzes = allQuizzes.size();
            int startIndex = page * size;
            int endIndex = Math.min(startIndex + size, totalQuizzes);

            if (startIndex >= totalQuizzes) {
                System.out.println("Start index out of range for: " + username);
                return new ResponseEntity<>(
                        QuizListDto.builder().quizs(new ArrayList<>())
                                .page((long)page)
                                .isLast(true)
                                .build(), HttpStatus.OK);
            }

            List<OwnerQuizDto> paginatedQuizzes = allQuizzes.subList(startIndex, endIndex);
            System.out.println(paginatedQuizzes.toString());
            var quizList = QuizListDto.builder()
                    .quizs(paginatedQuizzes.stream().map(p -> {return p.getQuiz();}).collect(Collectors.toList()))
                    .page((long) page)
                    .isLast(paginatedQuizzes.size() < size)
                    .build();
            return new ResponseEntity<>(quizList, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error retrieving shuffled paginated quizzes of followers: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<OwnerQuizDto> fetchQuizzes(String username) {
        System.out.println("Fetching quizzes for: " + username);
        HttpHeaders headers = new HttpHeaders();
        headers.set(SecurityConstants.AUTHORIZED_USER_HEADER, username);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        List<OwnerQuizDto> allQuizzes = new ArrayList<>();
        try {
            String url = "lb://users-service/followed";
            System.out.println("Making restTemplate exchange to: " + url);
            ResponseEntity<List<String>> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<String>>() {});

            System.out.println("Response status code: " + response.getStatusCode());
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                List<String> followerUsernames = response.getBody();
                System.out.println("Follower usernames: " + followerUsernames);
                for (String followerUsername : followerUsernames) {
                    System.out.println("Getting quizzes for follower: " + followerUsername);
                    List<Quiz> quizzes = quizService.getQuizByOwner(followerUsername);
                    if (quizzes != null && !quizzes.isEmpty()) {
                        for (Quiz quiz : quizzes) {
                            QuizDto quizDto = DtoConv.quizToQuizDto(quiz);
                            allQuizzes.add(new OwnerQuizDto(followerUsername, quizDto));
                            System.out.println("Added quiz: " + quiz.getTitle() + " for follower: " + followerUsername);
                        }
                    } else {
                        System.out.println("No quizzes found or null returned for follower: " + followerUsername);
                    }
                }
            } else {
                System.err.println("Error or null response from 'lb://users-service/followed'");
            }
        } catch (Exception e) {
            System.err.println("Exception in fetchQuizzes: " + e.getMessage());
            e.printStackTrace();
        }

        Collections.shuffle(allQuizzes);

        // Update the cache with the new list
        quizCache.put(username, allQuizzes);

        return allQuizzes;
    }









    @PostMapping("/create")
    public ResponseEntity<CreateQuizResponse> createQuiz(@RequestHeader(name = SecurityConstants.AUTHORIZED_USER_HEADER) String authorizedUser, @Valid @RequestBody CreateQuizRequest quizDto){
        try{
            Quiz quiz = new Quiz();
            quiz.setTitle(quizDto.getTitle());
            quiz.setOwner(authorizedUser);
            quiz.setCreatedAt(Instant.now());
            quiz.setExpiresAt(quizDto.getExpiryDate());
            List<Question> questions = quizDto.getQuestionsList().stream()
                    .map(questionDto -> {
                        Question question = new Question();
                        question.setText(questionDto.getText());
                        question.setQuiz(quiz);
                        List<Answer> answers = questionDto.getAnswers().stream()
                                .map(answerDto -> {
                                    Answer answer = new Answer();
                                    answer.setText(answerDto.getText());
                                    answer.setCorrect(answerDto.isCorrect());
                                    answer.setQuestion(question);
                                    return answer;
                                })
                                .collect(Collectors.toList());
                        question.setAnswers(answers);
                        return question;
                    })
                    .collect(Collectors.toList());
            quiz.setQuestions(questions);
            quizService.saveQuiz(quiz);
            return new ResponseEntity<>(new CreateQuizResponse(),HttpStatus.CREATED);
        } catch (Exception e){
            System.out.println("error creating quiz" + e.getMessage());
            return new ResponseEntity<CreateQuizResponse>(new CreateQuizResponse(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}