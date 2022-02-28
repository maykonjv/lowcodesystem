import { getAPIClient } from './axios'

export interface UserType {
  _id?: string
  firstname: string
  lastname: string
  email: string
  password: string
  created_at?: Date
  updated_at?: Date
  deleted_at?: Date
}

export interface LoginType {
  email: string
  password: string
}

export interface MenuType {
  _id?: string
  name: string
  page: string
  icon: string
  parent: MenuType
  created_at?: Date
  updated_at?: Date
}

export const api = {
  config: getAPIClient(),
  login: async (body: LoginType) => {
    return await getAPIClient().post('/api/login', body, {
      validateStatus: status => status < 500
    })
  },
  getUsers: async () => {
    return await getAPIClient().get('/api/user', {
      validateStatus: status => status < 500
    })
  },
  postUser: async (body: UserType) => {
    return await getAPIClient().post('/api/user', body, {
      validateStatus: status => status < 500
    })
  },
  getUser: async (id: string) => {
    return await getAPIClient().get(`/api/user/${id}`, {
      validateStatus: status => status < 500
    })
  },
  putUser: async (id: string, body: UserType) => {
    return await getAPIClient().put(`/api/user/${id}`, body, {
      validateStatus: status => status < 500
    })
  },
  deleteUser: async (id: string) => {
    return await getAPIClient().delete(`/api/user/${id}`, {
      validateStatus: status => status < 500
    })
  },
  // menu
  getMenus: async () => {
    return await getAPIClient().get('/api/menu', {
      validateStatus: status => status < 500
    })
  },
  postMenu: async (body: MenuType) => {
    return await getAPIClient().post('/api/menu', body, {
      validateStatus: status => status < 500
    })
  },
  getMenu: async (id: string) => {
    return await getAPIClient().get(`/api/menu/${id}`, {
      validateStatus: status => status < 500
    })
  },
  putMenu: async (id: string, body: MenuType) => {
    return await getAPIClient().put(`/api/menu/${id}`, body, {
      validateStatus: status => status < 500
    })
  },
  deleteMenu: async (id: string) => {
    return await getAPIClient().delete(`/api/menu/${id}`, {
      validateStatus: status => status < 500
    })
  }
}
