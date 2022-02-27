import Router from 'next/router'
import { useState } from 'react'
import { api } from '../helpers/api'

export const useLogin = () => {
  const [state, setState] = useState({
    email: '',
    password: '',
    error: null,
    loading: false,
    success: false
  })

  const handleChange = event => {
    const { name, value } = event.target
    setState({
      ...state,
      [name]: value
    })
  }

  const handleSubmit = async event => {
    event.preventDefault()
    setState({
      ...state,
      error: null,
      loading: true
    })

    try {
      const { email, password } = state
      if (!email || !password) {
        throw new Error('All fields are required')
      }
      const resp = await api.login({ email, password })
      const { ok } = resp
      const { message } = await resp.json()
      if (!ok) {
        throw new Error(`${message}`)
      }
      Router.push('/home')
    } catch (error) {
      console.log(error)
      setState({
        ...state,
        error: error.message,
        loading: false
      })
    }
  }

  return {
    state,
    handleChange,
    handleSubmit
  }
}
