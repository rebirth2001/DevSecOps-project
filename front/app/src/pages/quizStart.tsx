import { useNavigate } from "react-router";
import LayoutDashboard from "../layouts/layout-dashboard";
import { useState } from "react";
import axios from "axios";

export default function QuizStart() {
  const navigate = useNavigate();
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [questionNumber, setQNumber] = useState("");
  const [success, setSuccess] = useState("")
  const handleSubmit =async (e:React.FormEvent) => {
    e.preventDefault();
    try{
      const response = await axios.post("http://localhost:8080/api/v1/quizzes",
      JSON.stringify({ title, description, questionNumber: parseInt(questionNumber, 10) }),
      {
        headers: {'Content-Type': 'application/json'},
        withCredentials: false
      });
      console.log(response.data)
      navigate(`/quizContent/${response.data?.questionNumber}`);
    }
    catch(err){
      console.error(err);
    }
  }
  return (
    <LayoutDashboard>
      <div className="mt-4 flex flex-col bg-gray-100 rounded-lg p-4 shadow-sm">
        <h2 className="ai-story-maker-dream-form text-black font-bold text-2xl">Quiz Start</h2>
        <form onSubmit={handleSubmit}>
        <div className="mt-4">
            <label className="text-black" htmlFor="title">Title</label>
            <input placeholder="Enter a title for your quiz" 
              className="w-full bg-white rounded-md border-gray-300 text-black px-2 py-1" 
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              type="text"/>
        </div>

        <div className="mt-4">
            <label className="text-black" htmlFor="description">Description</label>
            <textarea placeholder="Describe your quiz in detail" 
            className="w-full bg-white rounded-md border-gray-300 text-black px-2 py-1" 
            id="description"
            onChange={(e) => setDescription(e.target.value)}
            value={description}></textarea>
            
        </div>

        <div className="mt-4 flex flex-row space-x-2">
            <div className="flex-1">
            <label className="text-black" htmlFor="emotions">Question Number</label>
            <input  className="w-full bg-white rounded-md border-gray-300 text-black px-2 py-1" 
            id="emotions" 
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
  );
}
