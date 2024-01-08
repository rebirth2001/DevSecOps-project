import { useState } from "react";
import "./Quiz.css";
import { Avatar, Flex, Progress, RadioChangeEvent } from "antd";
import { Link } from "react-router-dom";
import { getAvatarColor } from "../../utils/colors";
import { formatDateTime } from "../../utils/helpers";

import { Radio, Button } from "antd";
const RadioGroup = Radio.Group;

export interface QuizProps {
  quiz: QuizResponse;
}

function QuizView(props: QuizProps) {
  const [progress, setProgress] = useState(0);
  const [answer, setAnswer] = useState<null | number>(null);
  const [quizAnswers, setQuizAnswers] = useState<QuizAnswers>({
    quizID: props.quiz.id,
    answers: [],
  });
  const [score, setScore] = useState<null | number>(null);

  const incrProgress = () => {
    setProgress(progress + 1);
  };

  const handleAnswerChange = (newAnswer: number) => {
    setAnswer(newAnswer);
  };

  const handleAnswerSubmit = () => {
    if (progress < props.quiz.questions.length - 1) {
      const questionId = props.quiz.questions[progress].id;
      const choiceId = answer!;
      const newQuizAnswers = quizAnswers.answers;
      newQuizAnswers.push({
        questionId,
        choiceId,
      });
      setQuizAnswers({
        ...quizAnswers,
        answers: newQuizAnswers,
      });
      setAnswer(null);
      incrProgress();
    } else {
      incrProgress();
      console.log(progress === props.quiz.questions.length - 1);
      setScore(80);
    }
  };

  const getTimeRemaining = (quiz: QuizResponse): string => {
    const expirationTime = new Date(quiz.expirationDateTime).getTime();
    const currentTime = new Date().getTime();

    var difference_ms = expirationTime - currentTime;
    var seconds = Math.floor((difference_ms / 1000) % 60);
    var minutes = Math.floor((difference_ms / 1000 / 60) % 60);
    var hours = Math.floor((difference_ms / (1000 * 60 * 60)) % 24);
    var days = Math.floor(difference_ms / (1000 * 60 * 60 * 24));

    let timeRemaining;

    if (days > 0) {
      timeRemaining = days + " days left";
    } else if (hours > 0) {
      timeRemaining = hours + " hours left";
    } else if (minutes > 0) {
      timeRemaining = minutes + " minutes left";
    } else if (seconds > 0) {
      timeRemaining = seconds + " seconds left";
    } else {
      timeRemaining = "less than a second left";
    }
    return timeRemaining;
  };

  const quizChoices: JSX.Element[] = [];
  if (score === null && progress < props.quiz.questions.length) {
    props.quiz.questions[progress].choices.forEach((choice) => {
      quizChoices.push(
        <Radio className="quiz-choice-radio" key={choice.id} value={choice.id}>
          {choice.text}
        </Radio>
      );
    });
  }

  return (
    <div className="quiz-content">
      <div className="quiz-header">
        <div className="quiz-creator-info">
          <Link className="creator-link" to={`/users/${props.quiz.createdBy}`}>
            <Avatar
              className="quiz-creator-avatar"
              style={{
                backgroundColor: getAvatarColor(props.quiz.createdBy),
              }}
            >
              {props.quiz.createdBy.toUpperCase()}
            </Avatar>
            <span className="quiz-creator-username">
              @{props.quiz.createdBy}
            </span>
            <span className="quiz-creation-date">
              {formatDateTime(props.quiz.creationDateTime)}
            </span>
          </Link>
        </div>
        <div className="quiz-question">
          {score === null ? props.quiz.questions[progress].text : `Results`}
        </div>
      </div>
      <div className="quiz-choices">
        {score === null ? (
          <RadioGroup
            className="quiz-choice-radio-group"
            onChange={(e: RadioChangeEvent) => {
              handleAnswerChange(parseInt(e.target.value));
            }}
            value={answer}
          >
            {quizChoices}
          </RadioGroup>
        ) : (
          <Flex justify="center">
            <Progress percent={score} type="circle" />
          </Flex>
        )}
      </div>
      <Progress percent={(progress / props.quiz.questions.length) * 100} />
      <div className="quiz-footer">
        {progress < props.quiz.questions.length ? (
          <Button
            className="submit-button"
            disabled={!answer}
            onClick={(_) => {
              handleAnswerSubmit();
            }}
          >
            Submit
          </Button>
        ) : null}
        <span className="total-votes">{props.quiz.totalAttempts} attempts</span>
        <span className="separator">•</span>
        <span className="time-left">
          {props.quiz.isExpired ? "Quiz Expired" : getTimeRemaining(props.quiz)}
        </span>
      </div>
    </div>
  );
}

// function CompletedOrVotedQuizChoice(props) {
//   return (
//     <div className="cv-quiz-choice">
//       <span className="cv-quiz-choice-details">
//         <span className="cv-choice-percentage">
//           {Math.round(props.percentVote * 100) / 100}%
//         </span>
//         <span className="cv-choice-text">{props.choice.text}</span>
//         {props.isSelected ? (
//           <Icon className="selected-choice-icon" type="check-circle-o" />
//         ) : null}
//       </span>
//       <span
//         className={
//           props.isWinner
//             ? "cv-choice-percent-chart winner"
//             : "cv-choice-percent-chart"
//         }
//         style={{ width: props.percentVote + "%" }}
//       ></span>
//     </div>
//   );
// }

export default QuizView;
