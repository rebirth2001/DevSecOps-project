import { API_BASE_URL, QUIZ_LIST_SIZE, ACCESS_TOKEN } from "../constants";
import { UserProfile } from "../lib/user";

const request = (
  options: RequestOptions,
  isEmptyResponse: boolean = false
): Promise<any> => {
  const headers: HeadersInit = [["Content-Type", "application/json"]];

  if (localStorage.getItem(ACCESS_TOKEN)) {
    headers.push([
      "Authorization",
      `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
    ]);
  }

  const url: URL = new URL(options.url);
  const cfg: RequestInit = {
    headers: headers,
    method: options.method,
    body: options.body,
  };

  return fetch(url, cfg).then((response) => {
    if (isEmptyResponse) {
      return response;
    } else {
      return response.json().then((json) => {
        if (!response.ok) {
          return Promise.reject(json);
        }
        return json;
      });
    }
  });
};

export function login(loginRequest: LoginRequest): Promise<LoginResponse> {
  return request({
    url: API_BASE_URL + "/users/auth/sign-in",
    method: "POST",
    body: JSON.stringify(loginRequest),
  });
}

export function signup(signupRequest: SignupRequest): Promise<SignupResponse> {
  return request({
    url: API_BASE_URL + "/users/auth/sign-up",
    method: "POST",
    body: JSON.stringify(signupRequest),
  });
}

export function checkEmailAvailability(email: string): Promise<boolean> {
  return request({
    url:
      API_BASE_URL + "/users/validation/checkEmailAvailability?email=" + email,
    method: "GET",
  });
}

export function checkUsernameAvailability(username: string): Promise<boolean> {
  return request({
    url:
      API_BASE_URL +
      "/users/validation/checkUsernameAvailability?username=" +
      username,
    method: "GET",
  });
}

export function getCurrentUser(): Promise<UserProfile> {
  if (!localStorage.getItem(ACCESS_TOKEN)) {
    return Promise.reject("No access token set.");
  }

  return request({
    url: API_BASE_URL + "/users/me",
    method: "GET",
  });
}

export function checkIfUserFollows(username: string): Promise<boolean> {
  if (!localStorage.getItem(ACCESS_TOKEN)) {
    return Promise.reject("No access token set.");
  }

  return request({
    url: API_BASE_URL + "/users/does-follow/" + username,
    method: "GET",
  });
}

export function followUser(username: string): Promise<void> {
  if (!localStorage.getItem(ACCESS_TOKEN)) {
    return Promise.reject("No access token set.");
  }

  return request(
    {
      url: API_BASE_URL + "/users/follow/" + username,
      method: "GET",
    },
    true
  );
}

export function unfollowUser(username: string): Promise<void> {
  if (!localStorage.getItem(ACCESS_TOKEN)) {
    return Promise.reject("No access token set.");
  }

  return request(
    {
      url: API_BASE_URL + "/users/unfollow/" + username,
      method: "GET",
    },
    true
  );
}

export function getUserProfile(username: string): Promise<UserProfile> {
  return request({
    url: API_BASE_URL + "/users/profile/" + username,
    method: "GET",
  });
}

export function findUserProfile(username: string): Promise<UserProfile[]> {
  if (!localStorage.getItem(ACCESS_TOKEN)) {
    return Promise.reject("No access token set.");
  }

  return request(
    {
      url: API_BASE_URL + "/users/find/" + username,
      method: "GET",
    },
  );
}

export function getUserQuizProfile(username: string): Promise<UserQuizProfile> {
  return new Promise<UserQuizProfile>((resolve, reject) => {
    resolve({
      quizTaken: 1,
      quizCreated: 4,
    });
  });
}

export function getAllQuizs(page: number, size: number) {
  page = page || 0;
  size = size || QUIZ_LIST_SIZE;

  return request({
    url: API_BASE_URL + "/polls?page=" + page + "&size=" + size,
    method: "GET",
  });
}

export function createQuiz(quizData: Quiz): Promise<Response> {
  return request({
    url: API_BASE_URL + "/quizs/create",
    method: "POST",
    body: JSON.stringify(quizData),
  });
}

/*
 *  TO DO.
 */

// export function castVote(voteData) {
//   return request({
//     url: API_BASE_URL + "/polls/" + voteData.pollId + "/votes",
//     method: "POST",
//     body: JSON.stringify(voteData),
//   });
// }

export function getUserCreatedQuizs(
  username: string,
  page: number,
  size: number
): Promise<QuizListResponse> {
  page = page || 0;
  size = size || QUIZ_LIST_SIZE;

  return request({
    url:
      API_BASE_URL +
      "/users/" +
      username +
      "/polls?page=" +
      page +
      "&size=" +
      size,
    method: "GET",
  });
}

export function getUserTakenQuizs(
  username: string,
  page: number,
  size: number
): Promise<QuizListResponse> {
  page = page || 0;
  size = size || QUIZ_LIST_SIZE;

  return request({
    url:
      API_BASE_URL +
      "/users/" +
      username +
      "/votes?page=" +
      page +
      "&size=" +
      size,
    method: "GET",
  });
}
