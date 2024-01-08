import {
  getAllQuizs,
  getUserCreatedQuizs,
  getUserTakenQuizs,
} from "../../utils/api";
import QuizView from "./Quiz";
//import { castVote } from "../util/APIUtils";
import LoadingIndicator from "../common/LoadingIndicator";
import { Button, notification } from "antd";
import { QUIZ_LIST_SIZE } from "../../constants";
import "./QuizList.css";
import { PlusCircleOutlined } from "@ant-design/icons";
import { useEffect, useState } from "react";
import { testQuizs } from "../../mock/quizs";

export interface QuizListProps {
  username: string | undefined;
  listType: string | undefined;
}

function QuizList(props: QuizListProps) {
  //   constructor(props) {
  //     super(props);
  //     this.state = {
  //       quizs: [],
  //       page: 0,
  //       size: 10,
  //       totalElements: 0,
  //       totalPages: 0,
  //       last: true,
  //       currentVotes: [],
  //       isLoading: false,
  //     };
  //     this.loadquizList = this.loadquizList.bind(this);
  //     this.handleLoadMore = this.handleLoadMore.bind(this);
  //   }
  const [quizs, setQuizs] = useState<QuizResponse[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [isLast, setIsLast] = useState<boolean>(false);
  const [page, setPage] = useState<number>(0);
  const [size, setSize] = useState<number>(10);

  const loadQuizList = (page: number = 0, size: number = QUIZ_LIST_SIZE) => {
    let promise: null | Promise<QuizListResponse> = null;
    if (props.username) {
      if (props.listType === "USER_CREATED_POLLS") {
        promise = getUserCreatedQuizs(props.username, page, size);
      } else if (props.listType === "USER_VOTED_POLLS") {
        promise = getUserTakenQuizs(props.username, page, size);
      }
    } else {
      promise = getAllQuizs(page, size);
    }

    if (!promise) {
      return;
    }

    setIsLoading(true);

    promise
      .then((response) => {
        const currentQuizs = quizs.slice();
        //const currentVotes = currentVotes.slice();

        // this.setState({
        //   quizs: quizs.concat(response),
        //   page: response.page,
        //   size: response.size,
        //   totalElements: response.totalElements,
        //   totalPages: response.totalPages,
        //   last: response.last,
        //   currentVotes: currentVotes.concat(
        //     Array(response.content.length).fill(null)
        //   ),
        //   isLoading: false,
        // });
        setQuizs(currentQuizs.concat(response.quizs));
        setPage(response.page);
        setIsLast(response.isLast);
        setIsLoading(false);
      })
      .catch((error) => {
        setQuizs(testQuizs);
        setPage(0);
        setIsLast(false);
        setIsLoading(false);
      });
  };

  useEffect(() => {
    loadQuizList();
  }, []);

  //   componentDidUpdate(nextProps) {
  //     if (this.props.isAuthenticated !== nextProps.isAuthenticated) {
  //       // Reset State
  //       this.setState({
  //         quizs: [],
  //         page: 0,
  //         size: 10,
  //         totalElements: 0,
  //         totalPages: 0,
  //         last: true,
  //         currentVotes: [],
  //         isLoading: false,
  //       });
  //       this.loadquizList();
  //     }
  //   }

  const handleLoadMore = () => {
    loadQuizList(page + 1);
  };

  //   handleVoteChange(event, quizIndex) {
  //     const currentVotes = this.state.currentVotes.slice();
  //     currentVotes[quizIndex] = event.target.value;

  //     this.setState({
  //       currentVotes: currentVotes,
  //     });
  //   }

  //   handleVoteSubmit(event, quizIndex) {
  //     event.preventDefault();
  //     if (!this.props.isAuthenticated) {
  //       this.props.history.push("/login");
  //       notification.info({
  //         message: "quizing App",
  //         description: "Please login to vote.",
  //       });
  //       return;
  //     }

  //     const quiz = this.state.quizs[quizIndex];
  //     const selectedChoice = this.state.currentVotes[quizIndex];

  //     const voteData = {
  //       quizId: quiz.id,
  //       choiceId: selectedChoice,
  //     };

  //     castVote(voteData)
  //       .then((response) => {
  //         const quizs = this.state.quizs.slice();
  //         quizs[quizIndex] = response;
  //         this.setState({
  //           quizs: quizs,
  //         });
  //       })
  //       .catch((error) => {
  //         if (error.status === 401) {
  //           this.props.handleLogout(
  //             "/login",
  //             "error",
  //             "You have been logged out. Please login to vote"
  //           );
  //         } else {
  //           notification.error({
  //             message: "quizing App",
  //             description:
  //               error.message || "Sorry! Something went wrong. Please try again!",
  //           });
  //         }
  //       });
  //   }

  const quizViews: JSX.Element[] = [];
  quizs.forEach((quiz) => {
    quizViews.push(
      <QuizView
        key={quiz.id}
        quiz={quiz}
        // currentVote={this.state.currentVotes[quizIndex]}
        // handleVoteChange={(event) => this.handleVoteChange(event, quizIndex)}
        // handleVoteSubmit={(event) => this.handleVoteSubmit(event, quizIndex)}
      />
    );
  });

  return (
    <div className="quizs-container">
      {quizViews}
      {!isLoading && quizs.length === 0 ? (
        <div className="no-quizs-found">
          <span>No quizs Found.</span>
        </div>
      ) : null}
      {!isLoading && !isLast ? (
        <div className="load-more-quizs">
          <Button type="dashed" onClick={handleLoadMore} disabled={isLoading}>
            <PlusCircleOutlined />
            Load more.
          </Button>
        </div>
      ) : null}
      {isLoading ? <LoadingIndicator /> : null}
    </div>
  );
}

export default QuizList;
