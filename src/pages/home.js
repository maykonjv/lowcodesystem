import React from 'react'
import { styled, useStyletron } from 'baseui'
import TemplateHeader from '../components/TemplateHeader'
import Content from './custom'
import { menuData } from '../assets/constants'

const Home = () => {
  const [open, setOpen] = React.useState(false)
  const [page, setPage] = React.useState()
  const [active, setActive] = React.useState(null)
  const [css] = useStyletron()

  React.useEffect(() => {
    async function fetchData() {
      const response = await fetch(
        'https://api.github.com/repos/facebook/create-react-app/commits'
      )
      const json = await response.json()
      console.log(json)
    }
    fetchData()
  }, [])

  return (
    <TemplateWrapper>
      <SidebarWrapper
        className={css({
          '@media (max-width: 768px)': {
            display: open ? 'block' : 'none'
          }
        })}
      >
        <div
          className={css({
            position: 'fixed',
            top: '0',
            left: '0',
            width: '100vw',
            background: 'rgba(0, 0, 0, 0.5)',
            height: '100vh',
            zIndex: '-1',
            display: 'none',
            '@media (max-width: 768px)': {
              display: open ? 'block' : 'none'
            }
          })}
          onClick={() => setOpen(false)}
        />
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
          Template Kit
        </Logo>
        {menuData.map(({ icon, title, page }, index) => {
          return (
            <StyledMenuItem
              key={index}
              $active={active === index}
              title={title}
              onClick={() => {
                setPage(page)
                setActive(index)
                setOpen(false)
              }}
            >
              {icon}
              {title}
            </StyledMenuItem>
          )
        })}
      </SidebarWrapper>
      <TemplateHeader open={open} setOpen={setOpen} />
      <Content pageID={page} />
    </TemplateWrapper>
  )
}
export default Home

const TemplateWrapper = styled('section', {
  display: 'flex',
  flexDirection: 'column',
  alignItems: 'flex-start',
  background: '#F7F8FC',
  position: 'relative',
  paddingLeft: '285px',
  paddingRight: '2rem',
  width: '100%',
  minHeight: '100vh',
  maxWidth: '100vw',
  boxSizing: 'border-box',
  '@media (max-width: 768px)': {
    paddingLeft: '0'
  }
})
const SidebarWrapper = styled('section', {
  position: 'fixed',
  top: '0',
  left: '0',
  width: '100%',
  maxWidth: '255px',
  height: '100vh',
  background: '#363740',
  zIndex: '1',
  overflowX: 'hidden'
})
const Logo = styled('div', {
  padding: '2.5rem 2rem',
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'center',
  fontSize: '1.25rem',
  color: '#f2f2f2',
  fontWeight: 'bold',
  boxSizing: 'border-box',
  background: 'none'
})
const StyledMenuItem = styled('div', props => ({
  padding: '1.25rem 2rem',
  background: props.$active ? '#9FA2B4' : 'none',
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'flex-start',
  fontSize: '1rem',
  color: props.$active ? '#DDE2FF' : '#A4A6B3',
  cursor: 'pointer',
  width: '100%',
  borderLeft: props.$active ? '4px solid #DDE2FF' : 'none',
  ':hover': {
    background: '#9FA2B4',
    color: '#DDE2FF',
    borderLeft: '4px solid #DDE2FF'
  }
}))
