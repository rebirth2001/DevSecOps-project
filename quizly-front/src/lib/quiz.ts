type Choice = {
  text: string;
  correct: boolean;
};

type Question = {
  text: string;
  choices: Choice[];
};

type QuizDuration = {
  days: number;
  hours: number;
};

type Quiz = {
  title: string;
  questions: Question[];
  length: number;
  duration: QuizDuration;
};

type Answer = {
  questionId: number;
  choiceId: number;
};

type QuizAnswers = {
  quizID: number;
  answers: Answer[];
};
