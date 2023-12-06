import React from "react";

export interface FormInputProps {
  type: string;
  name: string;
  label: string;
  placeholder: string | undefined;
  required: boolean;
  changeEventHandler: React.ChangeEventHandler<HTMLInputElement> | undefined;
}

export default function FormInput(props: FormInputProps) {
  return (
    <div className="py-2">
      <label
        htmlFor={props.label}
        className="text-sm font-semibold text-gray-800"
      >
        {props.label}
      </label>
      <input
        onChange={props.changeEventHandler}
        id={props.label}
        type={props.type}
        name={props.name}
        placeholder={props.placeholder}
        required={props.required}
        className="w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-800 rounded"
      />
    </div>
  );
}
