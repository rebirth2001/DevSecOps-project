import { Avatar, Button, Tabs, TabsProps, notification } from "antd";
import { useEffect, useState } from "react";
import { UserProfile } from "../../lib/user";
import { getAvatarColor } from "../../utils/colors";
import { formatDate } from "../../utils/helpers";
import {
  checkIfUserFollows,
  followUser,
  getUserProfile,
  getUserQuizProfile,
  unfollowUser,
} from "../../utils/api";
import LoadingIndicator from "../common/LoadingIndicator";
import NotFound from "../common/NotFound";
import ServerError from "../common/ServerError";
import { useParams } from "react-router-dom";

import "./profile.css";
import QuizList from "../quiz/QuizList";

export interface ProfileProps {
  currentUser: UserProfile;
  isAuthenticated: boolean;
}

export default function Profile(props: ProfileProps) {
  const params = useParams();
  const requestedUsername = params["username"] || props.currentUser.username;
  const loadUserProfile = (username: string) => {
    let quizsTaken = 0;
    let quizsCreated = 0;
    setIsLoading(true);
    if (props.currentUser.username !== requestedUsername) {
      checkIfUserFollows(requestedUsername)
        .then((response) => {
          setFollows(response);
        })
        .catch((_) => {
          setFollows(false);
        });
    }

    getUserProfile(username)
      .then(async (response) => {
        await getUserQuizProfile(username)
          .then((response) => {
            quizsCreated = response.quizCreated;
            quizsTaken = response.quizTaken;
          })
          .catch((_) => {
            //TODO:
          });
        response.quizsTaken = quizsTaken;
        response.quizsCreated = quizsCreated;
        setUser(response);
        setIsLoading(false);
      })
      .catch((error) => {
        if (error.status === 404) {
          setNotFound(true);
          setIsLoading(false);
        } else {
          setServerError(true);
          setIsLoading(false);
        }
      });
  };
  const [user, setUser] = useState<UserProfile | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const [notFound, setNotFound] = useState(false);
  const [serverError, setServerError] = useState(false);
  const [follows, setFollows] = useState(false);
  const tabBarStyle: React.CSSProperties = {
    textAlign: "center",
  };

  useEffect(() => {
    loadUserProfile(requestedUsername);
  }, []);

  if (isLoading) {
    return <LoadingIndicator />;
  }
  if (notFound) {
    return <NotFound />;
  }
  if (serverError) {
    return <ServerError />;
  }

  const handleFollowButtonClick = () => {
    let newFollowersCount = user!.followersCount;
    if (!follows) {
      setFollows(true);
      setUser({ ...user!, followersCount: ++newFollowersCount });
      followUser(requestedUsername)
        .then((_) => {
          notification.success({
            message: "Success",
            description: `You are now following ${requestedUsername}`,
          });
        })
        .catch((error) => {
          console.log(error);
          setFollows(false);
          setUser({ ...user!, followersCount: --newFollowersCount });
          notification.error({
            message: "Failure",
            description: `Couldn't follow ${requestedUsername}`,
          });
        });
    } else {
      setFollows(false);
      setUser({ ...user!, followersCount: --newFollowersCount });
      unfollowUser(requestedUsername)
        .then((_) => {
          notification.success({
            message: "Success",
            description: `You are no longer following ${requestedUsername}`,
          });
        })
        .catch((error) => {
          console.log(error);
          setFollows(true);
          setUser({ ...user!, followersCount: ++newFollowersCount });
          notification.error({
            message: "Failure",
            description: `Couldn't unfollow ${requestedUsername}`,
          });
        });
    }
  };

  const tabs: TabsProps["items"] = [
    {
      key: "1",
      label: `${user?.quizsCreated} Quizes created`,
      children: (
        <QuizList username={requestedUsername} listType="USER_CREATED_QUIZS" />
      ),
    },
    {
      key: "2",
      label: `${user?.quizsTaken} Quizes taken`,
      children: (
        <QuizList username={requestedUsername} listType="USER_TAKEN_QUIZS" />
      ),
    },
  ];

  return (
    <div className="profile">
      {user ? (
        <div className="user-profile">
          <div className="user-details">
            <div className="user-avatar">
              <Avatar
                className="user-avatar-circle"
                style={{
                  backgroundColor: getAvatarColor(user.username),
                }}
              >
                {user.username.slice(0, 1).toUpperCase()}
              </Avatar>
            </div>
            <div className="user-summary">
              <div className="full-name">{user.username}</div>
              <div className="username">{user.email}</div>
              <div className="user-joined">
                Joined {formatDate(user.joinedAt)}
              </div>
              <div className="user-joined">
                {`followers: ${user.followersCount}`}
              </div>
              {requestedUsername !== props.currentUser.username ? (
                <Button
                  type={follows ? "default" : "primary"}
                  htmlType="button"
                  size="large"
                  className="button"
                  onClick={(_) => {
                    handleFollowButtonClick();
                  }}
                >
                  {follows ? "Unfollow" : "Follow"}
                </Button>
              ) : (
                <></>
              )}
            </div>
          </div>
          <div className="user-poll-details">
            <Tabs
              items={tabs}
              defaultActiveKey="1"
              animated={false}
              tabBarStyle={tabBarStyle}
              size="large"
              className="profile-tabs"
            />
          </div>
        </div>
      ) : null}
    </div>
  );
}
