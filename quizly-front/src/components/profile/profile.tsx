import { Avatar, Tabs } from "antd";
import TabPane from "antd/es/tabs/TabPane";
import { useEffect, useState } from "react";
import { UserProfile } from "../../lib/user";
import { getAvatarColor } from "../../utils/colors";
import { formatDate } from "../../utils/helpers";
import { getUserProfile } from "../../utils/api";
import LoadingIndicator from "../common/LoadingIndicator";
import NotFound from "../common/NotFound";
import ServerError from "../common/ServerError";
import { useParams } from "react-router-dom";

export interface ProfileProps {
  currentUser: UserProfile;
  isAuthenticated: boolean;
}
export default function Profile(props: ProfileProps) {
  const params = useParams();
  const loadUserProfile = (username: string) => {
    setIsLoading(true);
    getUserProfile(username)
      .then((response) => {
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
  const tabBarStyle: React.CSSProperties = {
    textAlign: "center",
  };

  useEffect(() => {
    loadUserProfile(params["username"]!);
  });

  if (isLoading) {
    return <LoadingIndicator />;
  }
  if (notFound) {
    return <NotFound />;
  }
  if (serverError) {
    return <ServerError />;
  }
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
                {user.username.toUpperCase()}
              </Avatar>
            </div>
            <div className="user-summary">
              <div className="full-name">{user.username}</div>
              <div className="username">@{user.email}</div>
              <div className="user-joined">
                Joined {formatDate(user.joinedAt)}
              </div>
            </div>
          </div>
          <div className="user-poll-details">
            <Tabs
              defaultActiveKey="1"
              animated={false}
              tabBarStyle={tabBarStyle}
              size="large"
              className="profile-tabs"
            >
              <TabPane tab={`${user.quizesCount} Polls`} key="1">
                {/* <PollList username={this.props.match.params.username} type="USER_CREATED_POLLS" /> */}
              </TabPane>
              <TabPane tab={`${user.quizesTaken} Votes`} key="2">
                {/* <PollList username={this.props.match.params.username} type="USER_VOTED_POLLS" /> */}
              </TabPane>
            </Tabs>
          </div>
        </div>
      ) : null}
    </div>
  );
}
