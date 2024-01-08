import { Form, Input, Button, Select, Col, notification, Checkbox } from "antd";
import { ValidateStatus } from "antd/es/form/FormItem";
import { ChangeEvent, FormEvent, useState } from "react";
const Option = Select.Option;
const FormItem = Form.Item;
const { TextArea } = Input;
import "./NewQuiz.css";
import { CloseCircleOutlined, PlusCircleOutlined } from "@ant-design/icons";
import {
  MAX_CHOICES,
  MAX_QUESTIONS,
  QUIZ_CHOICE_MAX_LENGTH,
  QUIZ_QUESTION_MAX_LENGTH,
} from "../../constants";
import { createQuiz } from "../../utils/api";
import { useNavigate } from "react-router-dom";
import { CheckboxChangeEvent } from "antd/es/checkbox";

type FieldValidation = {
  validateStatus: ValidateStatus;
  errorMsg: string | null;
};

type FormQuestionChoice = {
  text: string;
  correct: boolean;
  validation: FieldValidation;
};

type FormQuestion = {
  text: string;
  choices: FormQuestionChoice[];
  validation: FieldValidation;
  hasCorrectChoice: boolean;
};

export default function NewQuiz() {
  const navigate = useNavigate();
  const handleSubmit = (event: FormEvent) => {
    event.preventDefault();

    const quizData: Quiz = {
      title: quizTitle,
      questions: quizQuestions.map((value: FormQuestion) => {
        return {
          text: value.text,
          choices: value.choices.map((choice: FormQuestionChoice) => {
            return {
              text: choice.text,
              correct: choice.correct,
            };
          }),
        };
      }),
      length: quizQuestions.length,
      duration: {
        days: quizDuration.days,
        hours: quizDuration.hours,
      },
    };

    createQuiz(quizData)
      .then((response) => {
        console.log(response.status);
        navigate("/");
      })
      .catch((error: any) => {
        console.log(error);
        if (error.status === 401) {
          // TODO: handleLogout.
          navigate("/login");
        } else {
          notification.error({
            message: "Quizly Server",
            description: error.message || "Something went wrong",
          });
        }
      });
  };

  const addChoice = (questionNumber: number) => {
    const questions = quizQuestions.slice();
    const newChoices = questions[questionNumber].choices.concat({
      text: "",
      correct: false,
      validation: {
        errorMsg: null,
        validateStatus: "validating",
      },
    });

    questions[questionNumber] = {
      ...questions[questionNumber],
      choices: newChoices,
    };
    setQuizQuestions(questions);
  };

  const removeChoice = (questionNumber: number, choiceNumber: number) => {
    const questions = quizQuestions.slice();

    const newChoices = [
      ...questions[questionNumber].choices.slice(0, choiceNumber),
      ...questions[questionNumber].choices.slice(choiceNumber + 1),
    ];

    questions[questionNumber] = {
      ...questions[questionNumber],
      choices: newChoices,
    };
    setQuizQuestions(questions);
  };

  const validateChoice = (choiceText: string): FieldValidation => {
    if (choiceText.length === 0) {
      return {
        validateStatus: "error",
        errorMsg: "Please enter a choice!",
      };
    } else if (choiceText.length > QUIZ_CHOICE_MAX_LENGTH) {
      return {
        validateStatus: "error",
        errorMsg: `Choice is too long (Maximum ${QUIZ_CHOICE_MAX_LENGTH} characters allowed)`,
      };
    } else {
      return {
        validateStatus: "success",
        errorMsg: null,
      };
    }
  };

  const handleChoiceChange = (
    event: ChangeEvent<HTMLInputElement>,
    questionNumber: number,
    choiceNumber: number
  ) => {
    const newTextvalue = event.target.value;
    const questions = quizQuestions.slice();
    const newChoices = questions[questionNumber].choices;

    newChoices[choiceNumber] = {
      text: newTextvalue,
      validation: validateChoice(newTextvalue),
      correct: false, //TODO: is correct needs to be updated.
    };

    questions[questionNumber] = {
      ...questions[questionNumber],
      choices: newChoices,
    };
    setQuizQuestions(questions);
  };

  const handleChoiceCorrectChange = (
    event: CheckboxChangeEvent,
    questionNumber: number,
    choiceNumber: number
  ) => {
    const questions = quizQuestions.slice();
    const newChoices = questions[questionNumber].choices;
    newChoices[choiceNumber] = {
      ...newChoices[choiceNumber],
      correct: event.target.checked,
    };
    questions[questionNumber] = {
      ...questions[questionNumber],
      choices: newChoices,
      hasCorrectChoice: event.target.checked,
    };
    setQuizQuestions(questions);
  };

  const addQuestion = () => {
    setQuizQuestions(
      quizQuestions.concat({
        text: "",
        choices: [
          {
            text: "",
            validation: {
              errorMsg: null,
              validateStatus: "validating",
            },
            correct: false,
          },
          {
            text: "",
            validation: {
              errorMsg: null,
              validateStatus: "validating",
            },
            correct: false,
          },
        ],
        validation: {
          errorMsg: null,
          validateStatus: "validating",
        },
        hasCorrectChoice: false,
      })
    );
  };

  const removeQuestion = (questionNumber: number) => {
    setQuizQuestions(
      quizQuestions.filter((_: FormQuestion, index: number) => {
        return index !== questionNumber;
      })
    );
  };

  const validateQuestion = (questionText: string): FieldValidation => {
    if (questionText.length === 0) {
      return {
        validateStatus: "error",
        errorMsg: "Please enter your question!",
      };
    } else if (questionText.length > QUIZ_QUESTION_MAX_LENGTH) {
      return {
        validateStatus: "error",
        errorMsg: `Question is too long (Maximum ${QUIZ_QUESTION_MAX_LENGTH} characters allowed)`,
      };
    } else {
      return {
        validateStatus: "success",
        errorMsg: null,
      };
    }
  };

  const handleQuestionChange = (
    event: ChangeEvent<HTMLTextAreaElement>,
    questionNumber: number
  ) => {
    const questions = quizQuestions.slice();
    const newTextValue = event.target.value;
    questions[questionNumber] = {
      ...questions[questionNumber],
      text: newTextValue,
      validation: validateQuestion(newTextValue),
    };
    setQuizQuestions(questions);
  };

  const handleQuizDaysChange = (value: string) => {
    setQuizDuration({ ...quizDuration, days: parseInt(value) });
  };

  const handleQuizHoursChange = (value: string) => {
    setQuizDuration({ ...quizDuration, hours: parseInt(value) });
  };

  const isFormInvalid = () => {
    if (titleValidation.validateStatus !== "success") {
      return true;
    }

    if (quizDuration.days === 0 && quizDuration.hours === 0) {
      return true;
    }

    let flag = false;
    quizQuestions.forEach((value: FormQuestion) => {
      if (value.validation.validateStatus !== "success") {
        flag = true;
        return;
      }
      value.choices.forEach((choice: FormQuestionChoice) => {
        if (choice.validation.validateStatus !== "success") {
          flag = true;
          return;
        }
      });
    });
    return flag;
  };

  const validateTitle = (quizTitle: string): FieldValidation => {
    if (quizTitle.length === 0) {
      return {
        validateStatus: "error",
        errorMsg: "Please enter the Quiz title!",
      };
    } else if (quizTitle.length > QUIZ_QUESTION_MAX_LENGTH) {
      return {
        validateStatus: "error",
        errorMsg: `Title is too long (Maximum ${QUIZ_QUESTION_MAX_LENGTH} characters allowed)`,
      };
    } else {
      return {
        validateStatus: "success",
        errorMsg: null,
      };
    }
  };

  const [quizTitle, setQuizTitle] = useState("");
  const [titleValidation, setTitleValidation] = useState<FieldValidation>({
    errorMsg: null,
    validateStatus: "validating",
  });

  const [quizQuestions, setQuizQuestions] = useState<FormQuestion[]>([
    {
      text: "",
      choices: [
        {
          text: "",
          validation: {
            errorMsg: null,
            validateStatus: "validating",
          },
          correct: false,
        },
        {
          text: "",
          validation: {
            errorMsg: null,
            validateStatus: "validating",
          },
          correct: false,
        },
      ],
      validation: {
        validateStatus: "validating",
        errorMsg: "",
      },
      hasCorrectChoice: false,
    },
  ]);

  const [quizDuration, setQuizDuration] = useState<QuizDuration>({
    days: 1,
    hours: 0,
  });

  return (
    <div className="new-quiz-container">
      <h1 className="page-title">Create Quiz</h1>
      <div className="new-quiz-content">
        <Form onSubmitCapture={handleSubmit} className="create-quiz-form">
          <FormItem
            validateStatus={titleValidation.validateStatus}
            help={titleValidation.errorMsg}
            className="quiz-form-row"
          >
            <Input
              placeholder={"Title"}
              size="large"
              value={quizTitle}
              onChange={(event) => {
                setQuizTitle(event.target.value);
                setTitleValidation(validateTitle(quizTitle));
              }}
            />
          </FormItem>
          {quizQuestions.map((value: FormQuestion, index: number) => {
            return (
              <QuestionFormElement
                key={index}
                questionNumber={index}
                question={value}
                handleQuestionChange={handleQuestionChange}
                removeQuestion={removeQuestion}
                addChoice={addChoice}
                removeChoice={removeChoice}
                handleChoiceChange={handleChoiceChange}
                handleChoiceCorrectChange={handleChoiceCorrectChange}
              />
            );
          })}
          <FormItem className="quiz-form-row">
            <Col xs={32} sm={16}>
              Quiz Duration:
            </Col>
            <Col xs={24} sm={20}>
              <span style={{ marginRight: "18px" }}>
                <Select
                  placeholder="days"
                  defaultValue={"1"}
                  onChange={handleQuizDaysChange}
                  value={`${quizDuration.days}`}
                  style={{ width: 60 }}
                >
                  {Array.from(Array(8).keys()).map((i) => (
                    <Option key={i}>{i}</Option>
                  ))}
                </Select>{" "}
                &nbsp;Days
              </span>
              <span>
                <Select
                  placeholder="hours"
                  defaultValue={"0"}
                  onChange={handleQuizHoursChange}
                  value={`${quizDuration.hours}`}
                  style={{ width: 60 }}
                >
                  {Array.from(Array(24).keys()).map((i) => (
                    <Option key={i}>{i}</Option>
                  ))}
                </Select>{" "}
                &nbsp;Hours
              </span>
            </Col>
          </FormItem>
          <FormItem className="quiz-form-row">
            <Button
              type="dashed"
              size="large"
              onClick={addQuestion}
              disabled={quizQuestions.length === MAX_QUESTIONS}
              className="create-quiz-form-button"
            >
              <PlusCircleOutlined /> Add a question
            </Button>
          </FormItem>
          <FormItem className="quiz-form-row">
            <Button
              type="primary"
              htmlType="submit"
              size="large"
              disabled={isFormInvalid()}
              className="create-quiz-form-button"
            >
              Create Quiz
            </Button>
          </FormItem>
        </Form>
      </div>
    </div>
  );
}

interface QuestionFormElementProps {
  question: FormQuestion;
  questionNumber: number;
  handleQuestionChange: (
    event: ChangeEvent<HTMLTextAreaElement>,
    questionNumber: number
  ) => void;
  removeQuestion: (questionNumeber: number) => void;
  addChoice: (questionNumber: number) => void;
  removeChoice: (questionNumber: number, choiceNumber: number) => void;
  handleChoiceChange: (
    event: ChangeEvent<HTMLInputElement>,
    questionNumber: number,
    choiceNumber: number
  ) => void;
  handleChoiceCorrectChange: (
    event: CheckboxChangeEvent,
    questionNumber: number,
    choiceNumber: number
  ) => void;
}

const QuestionFormElement = (props: QuestionFormElementProps) => {
  const choiceViews: JSX.Element[] = [];
  props.question.choices.forEach(
    (choice: FormQuestionChoice, index: number) => {
      choiceViews.push(
        <QuizChoice
          key={index}
          choice={choice}
          questionNumber={props.questionNumber}
          choiceNumber={index}
          correctChecked={props.question.hasCorrectChoice}
          removeChoice={() => {
            props.removeChoice(props.questionNumber, index);
          }}
          handleChoiceChange={props.handleChoiceChange}
          handleChoiceCorrectChange={props.handleChoiceCorrectChange}
        />
      );
    }
  );
  return (
    <div>
      <h3>{`Question ${props.questionNumber + 1}:`}</h3>
      <FormItem
        validateStatus={props.question.validation.validateStatus}
        help={props.question.validation.errorMsg}
        className="quiz-form-row"
      >
        <TextArea
          placeholder="Enter your question"
          style={{ fontSize: "16px" }}
          autoSize={{ minRows: 3, maxRows: 6 }}
          name="question"
          className={props.questionNumber > 1 ? "optional-question" : ""}
          value={props.question.text}
          onChange={(event: ChangeEvent<HTMLTextAreaElement>) =>
            props.handleQuestionChange(event, props.questionNumber)
          }
        />
        {props.questionNumber > 1 ? (
          <CloseCircleOutlined
            className="dynamic-delete-button"
            disabled={props.questionNumber <= 1}
            onClick={() => props.removeQuestion(props.questionNumber)}
          />
        ) : null}
      </FormItem>
      {choiceViews}
      <FormItem className="quiz-form-row">
        <Button
          type="dashed"
          onClick={() => {
            props.addChoice(props.questionNumber);
          }}
          disabled={props.question.choices.length === MAX_CHOICES}
        >
          <PlusCircleOutlined /> Add a choice
        </Button>
      </FormItem>
    </div>
  );
};

interface QuizChoiceProps {
  choice: FormQuestionChoice;
  choiceNumber: number;
  questionNumber: number;
  correctChecked: boolean;
  handleChoiceChange: (
    event: ChangeEvent<HTMLInputElement>,
    questionNumber: number,
    choiceNumber: number
  ) => void;
  removeChoice: (choiceIndex: number) => void;
  handleChoiceCorrectChange: (
    event: CheckboxChangeEvent,
    questionNumber: number,
    choiceNumber: number
  ) => void;
}
function QuizChoice(props: QuizChoiceProps) {
  return (
    <FormItem
      validateStatus={props.choice.validation.validateStatus}
      help={props.choice.validation.errorMsg}
      className="quiz-form-row"
    >
      <Input
        placeholder={"Choice " + (props.choiceNumber + 1)}
        size="large"
        value={props.choice.text}
        className={props.choiceNumber > 1 ? "optional-choice" : ""}
        onChange={(event) =>
          props.handleChoiceChange(
            event,
            props.questionNumber,
            props.choiceNumber
          )
        }
      />
      {props.choiceNumber > 1 ? (
        <CloseCircleOutlined
          className="dynamic-delete-button"
          disabled={props.choiceNumber <= 1}
          onClick={() => props.removeChoice(props.choiceNumber)}
        />
      ) : null}
      <Checkbox
        checked={props.choice.correct}
        onChange={(event: CheckboxChangeEvent) => {
          props.handleChoiceCorrectChange(
            event,
            props.questionNumber,
            props.choiceNumber
          );
        }}
        disabled={props.correctChecked && props.choice.correct !== true}
      >
        Correct
      </Checkbox>
    </FormItem>
  );
}