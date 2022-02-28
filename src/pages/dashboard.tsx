import React, { useContext } from 'react'
import { Box } from '@chakra-ui/react'
import { GetServerSideProps } from 'next'
import { parseCookies } from 'nookies'
import Template from '../components/Template'
import { getAPIClient } from '../services/axios'
import { DashboardContext } from '../contexts/Dashboard.context'

export default function Dashboard() {
  const { page } = useContext(DashboardContext)
  return (
    <Template>
      <Box>{page}</Box>
    </Template>
  )
}

export const getServerSideProps: GetServerSideProps = async context => {
  const apiClient = getAPIClient(context)
  console.log(context.req.cookies)
  const { ['nextauth.token']: token } = parseCookies(context)

  if (!token) {
    return {
      redirect: {
        destination: '/',
        permanent: false
      }
    }
  }

  const user = await apiClient.get('/api/user?id=621cdea379d270fdc41c3fca')
  console.log('user dashboard:', JSON.stringify(user.data))
  return {
    props: {
      // user,
    }
  }
}
