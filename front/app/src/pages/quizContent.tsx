import React, { useState } from "react";
import LayoutDashboard from "../layouts/layout-dashboard";
import { useParams } from "react-router";

export default function QuizContent() {
const {questionNumber} = useParams<{questionNumber?: string}>();
const initialQuestions = Array.from({ length: parseInt(questionNumber ?? '0', 10) }, (_, index) => ({
    question: '',
    numAnswers: 0,
    answers: Array.from({ length: 0 }, () => ''),
}));

const [questions, setQuestions] = useState(initialQuestions);

const handleQuestionChange = (index: number, value: string) => {
    const newQuestions = [...questions];
    newQuestions[index].question = value;
    setQuestions(newQuestions);
};

const handleNumAnswersChange = (index: number, value: string) => {
    const newQuestions = [...questions];
    const newNumAnswers = parseInt(value, 10);
    newQuestions[index].numAnswers = newNumAnswers > 4 ? 4 : newNumAnswers;
    newQuestions[index].answers = newQuestions[index].answers.slice(0, newNumAnswers);
    setQuestions(newQuestions);
};

const handleAnswerChange = (questionIndex: number, answerIndex: number, value: string) => {
    const newQuestions = [...questions];
    newQuestions[questionIndex].answers[answerIndex] = value;
    setQuestions(newQuestions);
};

const handleSubmit = (event:React.FormEvent) => {
    event.preventDefault();
    console.log('Questions:', questions);
};

return (
    <LayoutDashboard>
        <div className="mt-4 flex flex-col bg-gray-100 rounded-lg p-4 shadow-sm">
            <h2 className="ai-story-maker-dream-form text-black font-bold text-2xl">Quiz content</h2>

            <form onSubmit={handleSubmit}>
                {questions.map((q, index) => (
                <div key={index} className="mockup-window border bg-base-300 p-5 mt-8">
                    <div className="mt-4">
                        <label className="text-black" htmlFor={`question${index + 1}`}>{`Question ${index + 1}`}</label>
                        <input
                            placeholder={`Enter question ${index + 1}`}
                            className="w-full bg-white rounded-md border-gray-300 text-black px-2 py-1"
                            type="text"
                            id={`question${index + 1}`}
                            value={q.question}
                            onChange={(e) => handleQuestionChange(index, e.target.value)}
                        />
                    </div>

                    <div className="mt-4 flex flex-row space-x-2">
                        <div className="flex-1">
                            <label className="text-black" htmlFor={`numAnswers${index + 1}`}>{`Number of answers (max 4)`}</label>
                            <input
                            className="w-full bg-white rounded-md border-gray-300 text-black px-2 py-1"
                            type="number"
                            id={`numAnswers${index + 1}`}
                            value={q.numAnswers}
                            onChange={(e) => handleNumAnswersChange(index, e.target.value)}
                            max={4}
                            min={2}
                            />
                        </div>
                    </div>

                    <div className="mt-4">
                    {Array.from({ length: q.numAnswers }).map((_, answerIndex) => (
                    <div key={answerIndex} className="mt-2">
                        <label className="text-black" htmlFor={`answer${index + 1}_${answerIndex + 1}`}>
                        {`Answer ${answerIndex + 1}`}
                        </label>
                        <input
                        placeholder={`Enter answer ${answerIndex + 1}`}
                        className="w-full bg-white rounded-md border-gray-300 text-black px-2 py-1"
                        type="text"
                        id={`answer${index + 1}_${answerIndex + 1}`}
                        value={q.answers[answerIndex]}
                        onChange={(e) => handleAnswerChange(index, answerIndex, e.target.value)}
                        />
                    </div>
                    ))}
                </div>

                <div className="mt-5">
                    <select className="select w-full max-w-xs">
                        <option disabled selected>Select the right answer</option>
                        {q.answers.map((answer, answerIndex) => (
                        <option key={answerIndex}>{answer}</option>
                        ))}
                    </select>
                </div>
                </div>
            ))}

            <div className="mt-4 flex flex-row space-x-2">
                <input
                    className="w-200 bg-white rounded-md border-gray-300 text-black px-2 py-1 bg-blue-500 hover:bg-blue-600 focus:outline-none rounded-lg px-6 py-2 text-white font-semibold shadow"
                    type="submit"
                    value="Submit"
                />
            </div>
            </form>
        </div>
    </LayoutDashboard>
);
}
