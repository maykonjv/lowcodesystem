import React from 'react'
import { useStyletron } from 'baseui'
import { useRegister } from '../hooks/register'

export const RegisterForm = () => {
  const [css] = useStyletron()
  const { handleChange, handleSubmit, state } = useRegister()

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
          placeholder="Password"
          onChange={handleChange}
        />
      </label>
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
        Register
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
        <span>Already have an account?</span>
        <a
          className={css({
            color: '#00a1ff',
            cursor: 'pointer',
            ':hover': {
              textDecoration: 'underline'
            },
            marginLeft: '0.5rem'
          })}
          href="/"
        >
          Sign in
        </a>
      </div>
    </form>
  )
}
