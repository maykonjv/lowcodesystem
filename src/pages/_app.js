import React from 'react'
import App from 'next/app'
import { Provider as StyletronProvider } from 'styletron-react'
import { styletron } from '../../styletron'
import { ThemeProvider } from 'baseui'
import { theme } from '../styles/theme'

export default class MyApp extends App {
  render() {
    const { Component, pageProps } = this.props
    return (
      <StyletronProvider value={styletron}>
        {/* <ThemeProvider theme={theme}> */}
        <Component {...pageProps} />
        {/* </ThemeProvider> */}
      </StyletronProvider>
    )
  }
}
