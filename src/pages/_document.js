import React from 'react'
import Document, { Html, Head, Main, NextScript } from 'next/document'
import { Provider as StyletronProvider } from 'styletron-react'
import { styletron } from '../../styletron'

class MyDocument extends Document {
  static async getInitialProps(context) {
    const renderPage = () =>
      context.renderPage({
        enhanceApp: App => props =>
          (
            <StyletronProvider value={styletron}>
              <App {...props} />
            </StyletronProvider>
          )
      })

    const initialProps = await Document.getInitialProps({
      ...context,
      renderPage
    })
    const stylesheets = styletron.getStylesheets() || []
    return { ...initialProps, stylesheets }
  }

  render() {
    return (
      <Html lang="pt">
        <Head>
          {this.props.stylesheets.map((sheet, i) => (
            <style
              className="_styletron_hydrate_"
              dangerouslySetInnerHTML={{ __html: sheet.css }}
              media={sheet.attrs.media}
              data-hydrate={sheet.attrs['data-hydrate']}
              key={i}
            />
          ))}
          <meta charSet="utf-8" />

          <link
            href="https://fonts.googleapis.com/css?family=Roboto:400,500,700"
            rel="stylesheet"
          />

          <link rel="icon" href="https://rocketseat.com.br/favicon.ico" />
        </Head>
        <body>
          <Main />
          <NextScript />
        </body>
      </Html>
    )
  }
}

export default MyDocument
