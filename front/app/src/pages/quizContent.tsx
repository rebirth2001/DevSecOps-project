import React, { useState } from "react";
import LayoutDashboard from "../layouts/layout-dashboard";

export default function QuizContent() {
    
    const [question, setQuestion] = useState('');
    const [numAnswers, setNumAnswers] = useState(0);
    const [answers, setAnswers] = useState(Array.from({ length: numAnswers }, () => ''));
  
    const handleNumAnswersChange = (event:React.ChangeEvent<HTMLInputElement>) => {
      const newNumAnswers = parseInt(event.target.value, 10);
      setNumAnswers(newNumAnswers > 4 ? 4 : newNumAnswers);
      setAnswers((prevAnswers) => prevAnswers.slice(0, newNumAnswers));
    };
  
    const handleAnswerChange = (index:number, value:string) => {
      const newAnswers = [...answers];
      newAnswers[index] = value;
      setAnswers(newAnswers);
    };
  
    const handleSubmit = (event:React.FormEvent<HTMLFormElement>) => {
      event.preventDefault();
      // Ajoutez ici la logique pour traiter les données du formulaire (question, réponses, etc.)
      console.log('Question:', question);
      console.log('Answers:', answers);
    };
  
    return (
      <LayoutDashboard>
        <div className="mt-4 flex flex-col bg-gray-100 rounded-lg p-4 shadow-sm">
          <h2 className="ai-story-maker-dream-form text-black font-bold text-2xl">Quiz quentent</h2>
  
          <form onSubmit={handleSubmit}>
            {/* debut de question */}
            <div className="mockup-window border bg-base-300 p-5 mt-8">
                <div className="mt-4">
                <label className="text-black" htmlFor="question">Question 1</label>
                <input
                    placeholder="Enter question 1"
                    className="w-full bg-white rounded-md border-gray-300 text-black px-2 py-1"
                    type="text"
                    id="question"
                    value={question}
                    onChange={(e) => setQuestion(e.target.value)}
                />
                </div>
    
                <div className="mt-4 flex flex-row space-x-2">
                <div className="flex-1">
                    <label className="text-black" htmlFor="numAnswers">Number of answers (max 4)</label>
                    <input
                    className="w-full bg-white rounded-md border-gray-300 text-black px-2 py-1"
                    type="number"
                    id="numAnswers"
                    value={numAnswers}
                    onChange={handleNumAnswersChange}
                    max={4}
                    min={2}
                    />
                </div>
                </div>
    
                <div className="mt-4">
                {Array.from({ length: numAnswers }).map((_, index) => (
                    <div key={index} className="mt-2">
                    <label className="text-black" htmlFor={`answer${index + 1}`}>{`Answer ${index + 1}`}</label>
                    <input
                        placeholder={`Enter answer ${index + 1}`}
                        className="w-full bg-white rounded-md border-gray-300 text-black px-2 py-1"
                        type="text"
                        id={`answer${index + 1}`}
                        value={answers[index]}
                        onChange={(e) => handleAnswerChange(index, e.target.value)}
                    />
                    </div>
                ))}
                </div>
                <div className="mt-5">
                    <select className="select w-full max-w-xs">
                        <option disabled selected>Select the right answer</option>
                        <option>answer 1</option>
                        <option>answer 2</option>
                        <option>answer 3</option>
                        <option>answer 4</option>
                    </select>

                </div>
            </div>
            {/* fin de question */}

            {/* debut de question */}
            <div className="mockup-window border bg-base-300 p-5 mt-8">
                <div className="mt-4">
                <label className="text-black" htmlFor="question">Question 2</label>
                <input
                    placeholder="Enter question 2"
                    className="w-full bg-white rounded-md border-gray-300 text-black px-2 py-1"
                    type="text"
                    id="question"
                    value={question}
                    onChange={(e) => setQuestion(e.target.value)}
                />
                </div>
    
                <div className="mt-4 flex flex-row space-x-2">
                <div className="flex-1">
                    <label className="text-black" htmlFor="numAnswers">Number of answers (max 4)</label>
                    <input
                    className="w-full bg-white rounded-md border-gray-300 text-black px-2 py-1"
                    type="number"
                    id="numAnswers"
                    value={numAnswers}
                    onChange={handleNumAnswersChange}
                    max={4}
                    min={2}
                    />
                </div>
                </div>
    
                <div className="mt-4">
                {Array.from({ length: numAnswers }).map((_, index) => (
                    <div key={index} className="mt-2">
                    <label className="text-black" htmlFor={`answer${index + 1}`}>{`Answer ${index + 1}`}</label>
                    <input
                        placeholder={`Enter answer ${index + 1}`}
                        className="w-full bg-white rounded-md border-gray-300 text-black px-2 py-1"
                        type="text"
                        id={`answer${index + 1}`}
                        value={answers[index]}
                        onChange={(e) => handleAnswerChange(index, e.target.value)}
                    />
                    </div>
                ))}
                </div>
                <div className="mt-5">
                    <select className="select w-full max-w-xs">
                        <option disabled selected>Select the right answer</option>
                        <option>answer 1</option>
                        <option>answer 2</option>
                        <option>answer 3</option>
                        <option>answer 4</option>
                    </select>

                </div>
            </div>
            {/* fin de question */}

            {/* debut de question */}
            <div className="mockup-window border bg-base-300 p-5 mt-8">
                <div className="mt-4">
                <label className="text-black" htmlFor="question">Question 3</label>
                <input
                    placeholder="Enter question 3"
                    className="w-full bg-white rounded-md border-gray-300 text-black px-2 py-1"
                    type="text"
                    id="question"
                    value={question}
                    onChange={(e) => setQuestion(e.target.value)}
                />
                </div>
    
                <div className="mt-4 flex flex-row space-x-2">
                <div className="flex-1">
                    <label className="text-black" htmlFor="numAnswers">Number of answers (max 4)</label>
                    <input
                    className="w-full bg-white rounded-md border-gray-300 text-black px-2 py-1"
                    type="number"
                    id="numAnswers"
                    value={numAnswers}
                    onChange={handleNumAnswersChange}
                    max={4}
                    min={2}
                    />
                </div>
                </div>
    
                <div className="mt-4">
                {Array.from({ length: numAnswers }).map((_, index) => (
                    <div key={index} className="mt-2">
                    <label className="text-black" htmlFor={`answer${index + 1}`}>{`Answer ${index + 1}`}</label>
                    <input
                        placeholder={`Enter answer ${index + 1}`}
                        className="w-full bg-white rounded-md border-gray-300 text-black px-2 py-1"
                        type="text"
                        id={`answer${index + 1}`}
                        value={answers[index]}
                        onChange={(e) => handleAnswerChange(index, e.target.value)}
                    />
                    </div>
                ))}
                </div>
                <div className="mt-5">
                    <select className="select w-full max-w-xs">
                        <option disabled selected>Select the right answer</option>
                        <option>answer 1</option>
                        <option>answer 2</option>
                        <option>answer 3</option>
                        <option>answer 4</option>
                    </select>

                </div>
            </div>
            {/* fin de question */}
            
  
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
