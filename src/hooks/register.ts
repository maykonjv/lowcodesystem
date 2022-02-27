import Router from 'next/router'
import { useState } from 'react'
import { api } from '../services/api'

export const useRegister = () => {
  const [state, setState] = useState({
    firstname: '',
    lastname: '',
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
      const { firstname, lastname, email, password, confirmPassword } = state
      if (password !== confirmPassword) {
        throw new Error('Passwords do not match')
      }
      if (!firstname || !lastname || !email || !password || !confirmPassword) {
        throw new Error('All fields are required')
      }
      console.log(state)
      const resp = await api.postUsers({ firstname, lastname, email, password })
      if (resp.statusText !== 'OK') {
        throw new Error(`${resp.data.message}`)
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
