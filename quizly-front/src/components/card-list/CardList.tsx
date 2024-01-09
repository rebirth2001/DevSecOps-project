import { UserProfile } from "../../lib/user";
import Card from "../card/Card";
import "./CardList.css";

interface CardListProps {
  users: UserProfile[];
}

export default function CardList(props: CardListProps) {
  return (
    <div className="card-list">
      {props.users.map((value) => {
        return <Card username={value.username} email={value.email} />;
      })}
    </div>
  );
}
