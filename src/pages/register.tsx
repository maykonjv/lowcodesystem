import React from 'react'
import { styled, useStyletron } from 'baseui'
import { RegisterForm } from '../components/RegisterForm'

export default function Register() {
  const [css] = useStyletron()
  return (
    <Background>
      <Wrapper>
        <Logo>
          <img
            className={css({
              width: '2rem',
              height: '2rem',
              marginRight: '0.5rem'
            })}
            src="/logo.svg"
            alt="logo"
          />
          Low-Code System
        </Logo>
        <RegisterForm />
      </Wrapper>
    </Background>
  )
}
const Background = styled('section', {
  background: '#f5f5f5',
  height: '100vh',
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'center'
})
const Wrapper = styled('div', {
  width: '100%',
  maxWidth: '400px',
  background: '#fff',
  borderRadius: '4px',
  boxShadow: '0px 0px 10px rgba(0, 0, 0, 0.1)',
  padding: '2rem',
  display: 'flex',
  flexDirection: 'column',
  alignItems: 'center',
  justifyContent: 'center'
})
const Logo = styled('div', {
  display: 'flex',
  alignItems: 'center',
  marginBottom: '2rem'
})
