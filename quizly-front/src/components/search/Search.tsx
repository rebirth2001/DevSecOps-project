import { useState, useEffect, ChangeEvent } from "react";
import "./Search.css";
import SearchBar from "../search-bar/SearchBar";
import CardList from "../card-list/CardList";
import { UserProfile } from "../../lib/user";
import { findUserProfile } from "../../utils/api";
import { notification } from "antd";

export default function Search() {
  const [searchTerm, setSearchTerm] = useState("");
  const [filteredUsers, setFilteredUsers] = useState<UserProfile[]>([]);
  const [isFetching, setIsFetching] = useState(false);

  useEffect(() => {
    if (searchTerm.trim().length === 0) {
      setFilteredUsers([]);
      return;
    }
    setIsFetching(true);
    findUserProfile(searchTerm.trim())
      .then((resp) => {
        setFilteredUsers(resp);
        setIsFetching(false);
      })
      .catch((_) => {
        notification.error({
          message: "Error",
          description: "Internal server error",
        });
        setIsFetching(false);
      });
  }, [searchTerm]);

  const handleSearch = (event: ChangeEvent<HTMLInputElement>) => {
    setSearchTerm(event.target.value.toLowerCase());
  };

  return (
    <div className="search">
      <h3 className="search-title">Discover other quiz makers</h3>
      <SearchBar
        placeholder="Ervin Howell"
        className="users-search"
        onChangeHandler={handleSearch}
        isFetching={isFetching}
      />
      <CardList users={filteredUsers} />
    </div>
  );
}
