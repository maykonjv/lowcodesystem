import { getAPIClient } from "./axios";

export interface UserType {
  _id?: string;
  firstname: string;
  lastname: string;
  email: string;
  password?: string;
}

export interface LoginType {
  email: string;
  password: string;
}

export const api = {
  config: getAPIClient(),
  login: async (body: LoginType) => {
    return await getAPIClient().post("/api/login", body, {validateStatus: (status) => status < 500});
  },
  getUsers: async ()=> {
    return await getAPIClient().get("/api/users", {validateStatus: (status) => status < 500});
  },
  postUsers: async (body: UserType)=> {
    return await getAPIClient().post("/api/users", body, {validateStatus: (status) => status < 500});
  },
  getUser: async (id: string)=> {
    return await getAPIClient().get(`/api/users/${id}`, {validateStatus: (status) => status < 500});
  },
  putUser: async (id: string, body: UserType)=> {
    return await getAPIClient().put(`/api/users/${id}`, body, {validateStatus: (status) => status < 500});
  },
  deleteUser: async (id: string)=> {
    return await getAPIClient().delete(`/api/users/${id}`, {validateStatus: (status) => status < 500});
  },
};
