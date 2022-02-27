import React from 'react'
import { useStyletron } from 'baseui'
import { useLogin } from '../hooks/login'

export const LoginForm = () => {
  const [css] = useStyletron()
  const { handleChange, handleSubmit, state } = useLogin()

  return (
    <form
      className={css({
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center'
      })}
    >
      <label
        className={css({
          display: 'flex',
          alignItems: 'left',
          justifyContent: 'center',
          marginBottom: '1rem',
          flexDirection: 'column'
        })}
      >
        <span
          className={css({
            display: 'flex',
            justifyContent: 'left',
            marginRight: '0.5rem',
            fontSize: '1rem'
          })}
        >
          Email
        </span>
        <input
          className={css({
            border: 'none',
            borderBottom: '1px solid #ccc',
            width: '100%',
            padding: '0.5rem',
            fontSize: '1.5rem',
            '@media (max-width: 768px)': {
              fontSize: '1rem'
            }
          })}
          type="email"
          name="email"
          placeholder="Email"
          required
          onChange={handleChange}
        />
      </label>
      <label
        className={css({
          display: 'flex',
          alignItems: 'left',
          justifyContent: 'center',
          marginBottom: '1rem',
          flexDirection: 'column'
        })}
      >
        <span
          className={css({
            display: 'flex',
            justifyContent: 'left',
            marginRight: '0.5rem',
            fontSize: '1rem'
          })}
        >
          Password
        </span>
        <input
          className={css({
            border: 'none',
            borderBottom: '1px solid #ccc',
            width: '100%',
            padding: '0.5rem',
            fontSize: '1.5rem',
            '@media (max-width: 768px)': {
              fontSize: '1rem'
            }
          })}
          required
          type="password"
          name="password"
          placeholder="Password"
          onChange={handleChange}
        />
      </label>
      {state.error && (
        <div
          className={css({
            color: 'red',
            fontSize: '1rem',
            marginBottom: '1rem'
          })}
        >
          {state.error}
        </div>
      )}
      <button
        className={css({
          background: '#00a1ff',
          color: '#fff',
          padding: '0.5rem',
          fontSize: '1.5rem',
          borderRadius: '4px',
          border: 'none',
          width: '100%',
          marginTop: '1rem',
          '@media (max-width: 768px)': {
            fontSize: '1rem'
          }
        })}
        onClick={handleSubmit}
      >
        Login
      </button>
      {/* Sign up */}
      <div
        className={css({
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          marginTop: '1rem',
          fontSize: '1rem'
        })}
      >
        {"Don't have an account?"}
        <a
          className={css({
            color: '#00a1ff',
            cursor: 'pointer',
            ':hover': {
              textDecoration: 'underline'
            },
            marginLeft: '0.5rem'
          })}
          href="/register"
        >
          Sign up
        </a>
      </div>
    </form>
  )
}
