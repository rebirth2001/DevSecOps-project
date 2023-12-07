import React, { useState } from 'react';
import Container from "../containter";

export default function HeroSection() {
  const [isPopupOpen, setIsPopupOpen] = useState(false);
  const [quizCode, setQuizCode] = useState('');

  const handleStartQuiz = () => {
    setIsPopupOpen(true);
  };

  const handleQuizCodeChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setQuizCode(event.target.value);
  };

  const handleEnterQuiz = () => {
    // Ajoutez ici la logique pour traiter le code de quiz
    console.log('Quiz Code entered:', quizCode);
    // Ajoutez ici la logique pour rediriger vers la page du quiz ou effectuer d'autres actions
    // ...

    // Fermez la popup après avoir traité le code de quiz
    setIsPopupOpen(false);
  };

  const closePopup = () => {
    setIsPopupOpen(false);
  };
  return (
    <div className="relative" id="home">
      <div
        aria-hidden="true"
        className="absolute inset-0 grid grid-cols-2 -space-x-52 opacity-40 dark:opacity-20"
      >
        <div className="blur-[106px] h-56 bg-gradient-to-br from-primary to-purple-400 dark:from-blue-700"></div>
        <div className="blur-[106px] h-32 bg-gradient-to-r from-cyan-400 to-sky-300 dark:to-indigo-600"></div>
      </div>
      <Container>
        <div className="relative pt-36 ml-auto">
          <div className="lg:w-2/3 text-center mx-auto">
            <h1 className="text-gray-900 dark:text-white font-bold text-5xl md:text-6xl xl:text-7xl">
              Make quizzes everyone can enjoy.
            </h1>
            <div className="mt-16 flex flex-wrap justify-center gap-y-4 gap-x-6">
              <a
                href="/sign-up"
                className="relative flex h-11 w-full items-center justify-center px-6 before:absolute before:inset-0 before:rounded-full before:bg-primary before:transition before:duration-300 hover:before:scale-105 active:duration-75 active:before:scale-95 sm:w-max"
              >
                <span className="relative text-base font-semibold text-white">
                  Get started
                </span>
              </a>
              <button
            onClick={handleStartQuiz}
            className="relative flex h-11 w-full items-center justify-center px-6 before:absolute before:inset-0 before:rounded-full before:border before:border-transparent before:bg-primary/10 before:bg-gradient-to-b before:transition before:duration-300 hover:before:scale-105 active:duration-75 active:before:scale-95 dark:before:border-gray-700 dark:before:bg-gray-800 sm:w-max"
          >
            <span className="relative text-base font-semibold text-primary dark:text-white">Start Quiz</span>
          </button>
            </div>
            <div className="hidden py-8 mt-16 border-y border-gray-100 dark:border-gray-800 sm:flex justify-between">
              <div className="text-left">
                <h6 className="text-lg font-semibold text-gray-700 dark:text-white">
                  The lowest price
                </h6>
                <p className="mt-2 text-gray-500">
                  Checkout our generous tiers
                </p>
              </div>
              <div className="text-left">
                <h6 className="text-lg font-semibold text-gray-700 dark:text-white">
                  Customizable Service
                </h6>
                <p className="mt-2 text-gray-500">
                  Quizzes can be customized to your need
                </p>
              </div>
              <div className="text-left">
                <h6 className="text-lg font-semibold text-gray-700 dark:text-white">
                  The most loved
                </h6>
                <p className="mt-2 text-gray-500">
                  1k+ customers and a growing community
                </p>
              </div>
            </div>
          </div>
        </div>
      </Container>
      {isPopupOpen && (
        <div className="fixed inset-0 z-50 flex items-center justify-center">
          <div className="absolute inset-0 bg-black opacity-40" onClick={closePopup}></div>
          <div className="bg-white p-8 rounded-lg z-10">
            <h2 className="text-xl font-semibold mb-4">Enter Quiz Code</h2>
            <input
              type="text"
              placeholder="Enter Participant name"
              className="w-full bg-gray-100 p-2 rounded-md mb-4"
            />
            <input
              type="text"
              placeholder="Enter quiz code"
              className="w-full bg-gray-100 p-2 rounded-md mb-4"
              value={quizCode}
              onChange={handleQuizCodeChange}
            />
            <button
              className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded-md mr-2"
              onClick={handleEnterQuiz}
            >
              Enter Quiz
            </button>
            <button
              className="bg-gray-300 hover:bg-gray-400 text-black px-4 py-2 rounded-md"
              onClick={closePopup}
            >
              Cancel
            </button>
          </div>
        </div>
      )}
    </div>
  );
}
