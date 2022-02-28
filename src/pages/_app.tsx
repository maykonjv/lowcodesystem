import '../styles/globals.css'
import { ChakraProvider } from '@chakra-ui/react'
import { AppProps } from 'next/app'
import { AuthProvider } from '../contexts/Auth.context'
import { DashboardProvider } from '../contexts/Dashboard.context'

function MyApp({ Component, pageProps }: AppProps) {
  return (
    <ChakraProvider>
      <AuthProvider>
        <DashboardProvider>
          <Component {...pageProps} />
        </DashboardProvider>
      </AuthProvider>
    </ChakraProvider>
  )
}

export default MyApp
