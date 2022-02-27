import { useContext, useState } from "react";
import { AuthContext } from "../contexts/AuthContext";
import { api } from "../services/api";

export const useLogin = () => {
  const { signIn } = useContext(AuthContext);
  const [state, setState] = useState({
    email: "",
    password: "",
    error: null,
    loading: false,
    success: false,
  });

  const handleChange = (event: any) => {
    const { name, value } = event.target;
    setState({
      ...state,
      [name]: value,
    });
  };

  const handleSubmit = async (event: any) => {
    event.preventDefault();
    setState({
      ...state,
      error: null,
      loading: true,
    });

    try {
      const { email, password } = state;
      if (!email || !password) {
        throw new Error("All fields are required");
      }
      const resp = await api.login({ email, password });
      console.log(resp.data.user);
      if (resp.statusText !== "OK") {
        throw new Error(`${resp.data.message}`);
      }
      signIn({ token: resp.data.token, user: resp.data.user });
    } catch (error: any) {
      console.log(error);
      setState({
        ...state,
        error: error.message,
        loading: false,
      });
    }
  };

  return {
    state,
    handleChange,
    handleSubmit,
  };
};
