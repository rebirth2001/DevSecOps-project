import { Button, Form, Input, notification } from "antd";
import FormItem, { ValidateStatus } from "antd/es/form/FormItem";
import { ChangeEvent, FormEvent, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import {
  EMAIL_MAX_LENGTH,
  PASSWORD_MAX_LENGTH,
  PASSWORD_MIN_LENGTH,
  USERNAME_MAX_LENGTH,
  USERNAME_MIN_LENGTH,
} from "../../constants";
import {
  checkEmailAvailability,
  checkUsernameAvailability,
  signup,
} from "../../utils/api";

type FieldValidation = {
  validateStatus: ValidateStatus;
  errorMsg: string | null;
};

type FormField = {
  value: string;
  validation: FieldValidation;
};
export default function SignUp() {
  const navigate = useNavigate();
  const validateEmail = (email: string): FieldValidation => {
    if (!email) {
      return {
        validateStatus: "error",
        errorMsg: "Email may not be empty",
      };
    }

    const EMAIL_REGEX = RegExp("[^@ ]+@[^@ ]+\\.[^@ ]+");
    if (!EMAIL_REGEX.test(email)) {
      return {
        validateStatus: "error",
        errorMsg: "Email not valid",
      };
    }

    if (email.length > EMAIL_MAX_LENGTH) {
      return {
        validateStatus: "error",
        errorMsg: `Email is too long (Maximum ${EMAIL_MAX_LENGTH} characters allowed)`,
      };
    }

    return {
      validateStatus: "success",
      errorMsg: null,
    };
  };

  const validateUsername = (username: string): FieldValidation => {
    if (username.length < USERNAME_MIN_LENGTH) {
      return {
        validateStatus: "error",
        errorMsg: `Username is too short (Minimum ${USERNAME_MIN_LENGTH} characters needed.)`,
      };
    } else if (username.length > USERNAME_MAX_LENGTH) {
      return {
        validateStatus: "error",
        errorMsg: `Username is too long (Maximum ${USERNAME_MAX_LENGTH} characters allowed.)`,
      };
    } else {
      return {
        validateStatus: "success",
        errorMsg: null,
      };
    }
  };

  const validatePassword = (password: string): FieldValidation => {
    if (password.length < PASSWORD_MIN_LENGTH) {
      return {
        validateStatus: "error",
        errorMsg: `Password is too short (Minimum ${PASSWORD_MIN_LENGTH} characters needed.)`,
      };
    } else if (password.length > PASSWORD_MAX_LENGTH) {
      return {
        validateStatus: "error",
        errorMsg: `Password is too long (Maximum ${PASSWORD_MAX_LENGTH} characters allowed.)`,
      };
    } else {
      return {
        validateStatus: "success",
        errorMsg: null,
      };
    }
  };

  const validateConfirmPassword = (cpassword: string): FieldValidation => {
    if (cpassword.length < PASSWORD_MIN_LENGTH) {
      return {
        validateStatus: "error",
        errorMsg: `Confirm password is too short (Minimum ${PASSWORD_MIN_LENGTH} characters needed.)`,
      };
    } else if (cpassword.length > PASSWORD_MAX_LENGTH) {
      return {
        validateStatus: "error",
        errorMsg: `Confirm password is too long (Maximum ${PASSWORD_MAX_LENGTH} characters allowed.)`,
      };
    } else if (cpassword != password.value) {
      return {
        validateStatus: "error",
        errorMsg: `Confirm password doesn't match the value of password.`,
      };
    } else {
      return {
        validateStatus: "success",
        errorMsg: null,
      };
    }
  };

  const validateUsernameAvailability = () => {
    // First check for client side errors in username
    const usernameValue = username.value;
    const usernameValidation = validateUsername(usernameValue);

    if (usernameValidation.validateStatus === "error") {
      setUsername({
        value: usernameValue,
        validation: usernameValidation,
      });
      return;
    }

    setUsername({
      value: usernameValue,
      validation: {
        validateStatus: "validating",
        errorMsg: null,
      },
    });

    checkUsernameAvailability(usernameValue)
      .then((response) => {
        if (response) {
          setUsername({
            value: usernameValue,
            validation: {
              validateStatus: "success",
              errorMsg: null,
            },
          });
        } else {
          setUsername({
            value: usernameValue,
            validation: {
              validateStatus: "error",
              errorMsg: "This username is already taken",
            },
          });
        }
      })
      .catch((_) => {
        // Marking validateStatus as success, Form will be recchecked at server
        setUsername({
          value: usernameValue,
          validation: {
            validateStatus: "success",
            errorMsg: null,
          },
        });
      });
  };

  const validateEmailAvailability = () => {
    // First check for client side errors in email
    const emailValue = email.value;
    const emailValidation = validateEmail(emailValue);

    if (emailValidation.validateStatus === "error") {
      setEmail({
        value: emailValue,
        validation: emailValidation,
      });
      return;
    }

    setEmail({
      value: emailValue,
      validation: {
        validateStatus: "validating",
        errorMsg: null,
      },
    });

    checkEmailAvailability(emailValue)
      .then((response) => {
        if (response) {
          setEmail({
            value: emailValue,
            validation: {
              validateStatus: "success",
              errorMsg: null,
            },
          });
        } else {
          setEmail({
            value: emailValue,
            validation: {
              validateStatus: "error",
              errorMsg: "This Email is already registered",
            },
          });
        }
      })
      .catch((error) => {
        console.log(error);
        // Marking validateStatus as success, Form will be recchecked at server
        setEmail({
          value: emailValue,
          validation: {
            validateStatus: "success",
            errorMsg: null,
          },
        });
      });
  };

  const [username, setUsername] = useState<FormField>({
    value: "",
    validation: {
      validateStatus: "validating",
      errorMsg: null,
    },
  });
  const [email, setEmail] = useState<FormField>({
    value: "",
    validation: {
      validateStatus: "validating",
      errorMsg: null,
    },
  });
  const [password, setPassword] = useState<FormField>({
    value: "",
    validation: {
      validateStatus: "validating",
      errorMsg: null,
    },
  });
  const [confirmPassword, setConfirmPassword] = useState<FormField>({
    value: "",
    validation: {
      validateStatus: "validating",
      errorMsg: null,
    },
  });

  const handleUsernameChange = (
    event: ChangeEvent<HTMLInputElement>,
    validationFun: (_: string) => FieldValidation
  ) => {
    const target = event.target;
    const inputValue = target.value;

    setUsername({
      value: inputValue,
      validation: validationFun(inputValue),
    });
  };

  const handleEmailChange = (
    event: ChangeEvent<HTMLInputElement>,
    validationFun: (_: string) => FieldValidation
  ) => {
    const target = event.target;
    const inputValue = target.value;

    setEmail({
      value: inputValue,
      validation: validationFun(inputValue),
    });
  };
  const handlePasswordChange = (
    event: ChangeEvent<HTMLInputElement>,
    validationFun: (_: string) => FieldValidation
  ) => {
    const target = event.target;
    const inputValue = target.value;

    setPassword({
      value: inputValue,
      validation: validationFun(inputValue),
    });
  };
  const handleConfirmPasswordChange = (
    event: ChangeEvent<HTMLInputElement>,
    validationFun: (_: string) => FieldValidation
  ) => {
    const target = event.target;
    const inputValue = target.value;

    setConfirmPassword({
      value: inputValue,
      validation: validationFun(inputValue),
    });
  };

  const handleSubmit = (event: FormEvent) => {
    event.preventDefault();

    const signupRequest: SignupRequest = {
      username: username.value,
      email: email.value,
      password: password.value,
      confirmPassword: confirmPassword.value,
    };

    signup(signupRequest)
      .then((response) => {
        if (response.isError) {
          notification.error({
            message: "Error",
            description: response.errors.pop(),
          });
        } else {
          notification.success({
            message: "Success",
            description:
              "Thank you! You're successfully registered. Please Login to continue!",
          });
          navigate("/log-in");
        }
      })
      .catch((error) => {
        notification.error({
          message: "Error",
          description:
            error.message || "Sorry! Something went wrong. Please try again!",
        });
      });
  };

  const isFormInvalid = () => {
    return !(
      username.validation.validateStatus === "success" &&
      email.validation.validateStatus === "success" &&
      password.validation.validateStatus === "success" &&
      confirmPassword.validation.validateStatus === "success"
    );
  };

  return (
    <div className="signup-container">
      <h1 className="page-title">Sign Up</h1>
      <div className="signup-content">
        <Form onSubmitCapture={handleSubmit} className="signup-form">
          <FormItem
            label="Username"
            hasFeedback
            validateStatus={username.validation.validateStatus}
            help={username.validation.errorMsg}
          >
            <Input
              size="large"
              name="username"
              autoComplete="off"
              placeholder="Your username"
              value={username.value}
              onBlur={validateUsernameAvailability}
              onChange={(event) =>
                handleUsernameChange(event, validateUsername)
              }
            />
          </FormItem>
          <FormItem
            label="Email"
            hasFeedback
            validateStatus={email.validation.validateStatus}
            help={email.validation.errorMsg}
          >
            <Input
              size="large"
              name="email"
              type="email"
              autoComplete="off"
              placeholder="Your email"
              value={email.value}
              onBlur={validateEmailAvailability}
              onChange={(event) => handleEmailChange(event, validateEmail)}
            />
          </FormItem>
          <FormItem
            label="Password"
            validateStatus={password.validation.validateStatus}
            help={password.validation.errorMsg}
          >
            <Input
              size="large"
              name="password"
              type="password"
              autoComplete="off"
              placeholder="A password between 6 to 20 characters"
              value={password.value}
              onChange={(event) =>
                handlePasswordChange(event, validatePassword)
              }
            />
          </FormItem>
          <FormItem
            label="Confirm Password"
            validateStatus={confirmPassword.validation.validateStatus}
            help={confirmPassword.validation.errorMsg}
          >
            <Input
              size="large"
              name="confirmPassword"
              type="password"
              autoComplete="off"
              placeholder="Same as password"
              value={confirmPassword.value}
              onChange={(event) =>
                handleConfirmPasswordChange(event, validateConfirmPassword)
              }
            />
          </FormItem>
          <FormItem>
            <Button
              type="primary"
              htmlType="submit"
              size="large"
              className="signup-form-button"
              disabled={isFormInvalid()}
            >
              Sign up
            </Button>
            Already registed? <Link to="/log-in">Login now!</Link>
          </FormItem>
        </Form>
      </div>
    </div>
  );
}

// Validation Functions

// validateName = (name) => {
//     if(name.length < NAME_MIN_LENGTH) {
//         return {
//             validateStatus: 'error',
//             errorMsg: `Name is too short (Minimum ${NAME_MIN_LENGTH} characters needed.)`
//         }
//     } else if (name.length > NAME_MAX_LENGTH) {
//         return {
//             validationStatus: 'error',
//             errorMsg: `Name is too long (Maximum ${NAME_MAX_LENGTH} characters allowed.)`
//         }
//     } else {
//         return {
//             validateStatus: 'success',
//             errorMsg: null,
//           };
//     }
// }
