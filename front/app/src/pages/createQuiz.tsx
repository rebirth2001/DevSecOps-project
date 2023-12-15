import axios from "axios";
import LayoutDashboard from "../layouts/layout-dashboard";
import React, { useState } from "react";

export default function CreateQuiz(){
    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    const [questionNumber, setQNumber] = useState("");
    const [success, setSuccess] = useState(true);
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
    const handleSubmit1 = async (e: React.FormEvent) => {
        e.preventDefault();
        console.log(title, description, questionNumber);
        setQuestions(Array.from({ length: parseInt(questionNumber ?? '0', 10) }, (_, index) => ({
            question: '',
            numAnswers: 0,
            answers: Array.from({ length: 0 }, () => ''),
        })));
        setSuccess(false);
    };
    const handleSubmit = async (event:React.FormEvent) => {
        event.preventDefault();
        const quizData = {
            title:title,
            description: description,
            questions: questions.map((q) => ({
                statement: q.question,
                answers: q.answers.map((answer) =>({
                    text: answer,
                    correct: false,
                })),
            })),
        };
        try {
            const response = await axios.post('http://localhost:8080/api/v1/quizzes/create', quizData);
            console.log(response.data);
            setSuccess(true);
        }catch (error){
            console.error('error creating quiz:', error);
        };
    };
    return(
        <>
        {success ? (
            <LayoutDashboard>
                    <div className="mt-4 flex flex-col bg-gray-100 rounded-lg p-4 shadow-sm">
                    <h2 className="ai-story-maker-dream-form text-black font-bold text-2xl">Quiz Start</h2>
                    <form onSubmit={handleSubmit1}>
                    <div className="mt-4">
                        <label className="text-black" htmlFor="title">Title</label>
                        <input placeholder="Enter a title for your quiz" 
                            className="w-full bg-white rounded-md border-gray-300 text-black px-2 py-1" 
                            value={title}
                            onChange={(e) => setTitle(e.target.value)}
                            type="text"/>
                            required
                    </div>
                    <div className="mt-4">
                        <label className="text-black" htmlFor="description">Description</label>
                        <textarea placeholder="Describe your quiz in detail" 
                            className="w-full bg-white rounded-md border-gray-300 text-black px-2 py-1" 
                            id="description"
                            required
                            onChange={(e) => setDescription(e.target.value)}
                            value={description}></textarea>
                        
                    </div>

                    <div className="mt-4 flex flex-row space-x-2">
                        <div className="flex-1">
                        <label className="text-black" htmlFor="emotions">Question Number</label>
                        <input  className="w-full bg-white rounded-md border-gray-300 text-black px-2 py-1" 
                            id="emotions" 
                            required
                            value={questionNumber}
                            onChange={(e) => setQNumber(e.target.value)}
                            type="number"/>
                        </div>
                    </div>
                    <div className="mt-4 flex flex-row space-x-2">
                        <input  className="w-200 bg-white rounded-md border-gray-300 text-black px-2 py-1 bg-blue-500 hover:bg-blue-600 focus:outline-none rounded-lg px-6 py-2  font-semibold shadow" 
                            id="emotions" 
                            type="submit"/>
                    </div>
                    </form>
                </div>
            </LayoutDashboard>
        ) :(
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
                                    required
                                    onChange={(e) => handleQuestionChange(index, e.target.value)}
                                />
                            </div>

                            <div className="mt-4 flex flex-row space-x-2">
                                <div className="flex-1">
                                    <label className="text-black" htmlFor={`numAnswers${index + 1}`}>{`Number of answers (max 4)`}</label>
                                    <input
                                    required
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
                                required
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
        )}
        
    </>
    );
}