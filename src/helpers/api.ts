interface UserType {
  name: string
  email: string
  password: string
}

interface LoginType {
  email: string
  password: string
}

export const api = {
  register: async (body: UserType): Promise<Response> => {
    return await fetch('/api/register', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(body)
    })
  },
  login: async (body: LoginType): Promise<Response> => {
    return await fetch('/api/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(body)
    })
  }
}
