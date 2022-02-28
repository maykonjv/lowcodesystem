import Router from 'next/router'
import React, { useState } from 'react'
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

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target
    setState({
      ...state,
      [name]: value
    })
  }

  const handleSubmit = async (event: React.MouseEvent<HTMLElement>) => {
    event.preventDefault()

    try {
      const { firstname, lastname, email, password, confirmPassword } = state
      if (password !== confirmPassword) {
        throw new Error('Passwords do not match')
      }
      if (!firstname || !lastname || !email || !password || !confirmPassword) {
        throw new Error('All fields are required')
      }
      console.log(state)
      const resp = await api.postUser({ firstname, lastname, email, password })
      if (resp.statusText !== 'OK') {
        throw new Error(`${resp.data.message}`)
      }
      setState({
        ...state,
        firstname: '',
        lastname: '',
        email: '',
        password: '',
        confirmPassword: '',
        error: null,
        loading: false,
        success: true
      })
    } catch (error: any) {
      setState({
        ...state,
        error: error.message,
        loading: false,
        success: false
      })
    }
  }

  return {
    state,
    handleChange,
    handleSubmit
  }
}
