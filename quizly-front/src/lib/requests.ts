type RequestOptions = {
  url: string;
  method: string;
  body?: string;
};

type LoginRequest = {
  email: string;
  password: string;
};

type LoginResponse = {
  accessToken: string;
};

type SignupRequest = {
  username: string;
  email: string;
  password: string;
  confirmPassword: string;
};

type SignupResponse = {
  errors: string[];
  isError: boolean;
};

type ChoiceResponse = {
  id: number;
  text: string;
};

type QuestionResponse = {
  id: number;
  text: string;
  choices: ChoiceResponse[];
};

type QuizResponse = {
  id: number;
  questions: QuestionResponse[];
  length: number;
  createdBy: string;
  creationDateTime: string;
  expirationDateTime: string;
  totalAttempts: number;
  isExpired: boolean;
};

type QuizListResponse = {
  quizs: QuizResponse[];
  isLast: boolean;
  page: number;
};
