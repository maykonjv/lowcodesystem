import Router from 'next/router'
import { useState } from 'react'
import { api } from '../helpers/api'

export const useRegister = () => {
  const [state, setState] = useState({
    name: '',
    email: '',
    password: '',
    confirmPassword: '',
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
      const { name, email, password, confirmPassword } = state
      if (password !== confirmPassword) {
        throw new Error('Passwords do not match')
      }
      if (!name || !email || !password || !confirmPassword) {
        throw new Error('All fields are required')
      }
      console.log(state)
      const resp = await api.register({ name, email, password })
      const { ok } = resp
      const { message } = await resp.json()
      if (!ok) {
        throw new Error(`${message}`)
      }
      Router.push('/')
    } catch (error) {
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
