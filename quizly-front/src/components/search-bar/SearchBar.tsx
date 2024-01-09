import React, { ChangeEvent, ChangeEventHandler, useState } from "react";
import { Input } from "antd";
import "./SearchBar.css";

const { Search } = Input;

export interface SearchBarProps {
  placeholder: string;
  className: string;
  isFetching: boolean;
  onChangeHandler: (event: ChangeEvent<HTMLInputElement>) => void;
}
export default function SearchBar(props: SearchBarProps) {
  const { placeholder, className, onChangeHandler, isFetching } = props;
  return (
    <Search
      placeholder={placeholder}
      className={`search-box ${className}`}
      onChange={(e) => onChangeHandler(e)}
      loading={isFetching}
    />
  );
}
