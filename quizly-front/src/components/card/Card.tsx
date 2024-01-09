import "./Card.css";
import { Avatar } from "antd";
import { getAvatarColor } from "../../utils/colors";
import { Link } from "react-router-dom";

export interface CardProps {
  username: string;
  email: string;
}

export function Card(props: CardProps) {
  return (
    <Link to={`/users/${props.username}`}>
      <div className="card-container">
        <h2>@{props.username}</h2>

        <Avatar
          className="user-avatar-circle"
          style={{
            backgroundColor: getAvatarColor(props.username),
          }}
        >
          {props.username.slice(0, 1).toUpperCase()}
        </Avatar>
        <p>{props.email}</p>
      </div>
    </Link>
  );
}

export default Card;
