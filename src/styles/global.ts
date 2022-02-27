import { createGlobalStyle } from 'styled-components'

export default createGlobalStyle`
  * {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
  }

  body {
    background: ${props => props.theme.colors.backgroundLight};
    color: ${props => props.theme.colors.dark};
    font: 400 16px Roboto, sans-serif;
  }
`
