import { createContext, useEffect, useState } from "react";
import { setCookie , parseCookies} from "nookies";
import Router from "next/router";

import { api, UserType } from "../services/api";

type SignInData = {
  token: string;
  user: UserType;
};

type AuthContextType = {
  isAuthenticated: boolean;
  user: UserType;
  signIn: (data: SignInData) => Promise<void>;
};

export const AuthContext = createContext({} as AuthContextType);

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [user, setUser] = useState<UserType>({} as UserType);

  const isAuthenticated = !!user;

    useEffect(() => {
      const { 'nextauth.token': token } = parseCookies()

      if (token) {
        // recoverUserInformation().then(response => {
        //   setUser(response.user)
        // })
      }
    }, [])

  async function signIn({ token, user }: SignInData) {
    setCookie(undefined, "nextauth.token", token, {
      maxAge: 60 * 60 * 1, // 1 hour
    });

    api.config.defaults.headers["Authorization"] = `Bearer ${token}`;

    setUser(user);

    Router.push("/dashboard");
  }

  return (
    <AuthContext.Provider value={{ user, isAuthenticated, signIn }}>
      {children}
    </AuthContext.Provider>
  );
}
