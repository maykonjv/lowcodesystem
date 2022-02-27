import { NextApiRequest, NextApiResponse } from 'next'
import Cors from 'cors'
import initMiddleware from '../../helpers/init-middleware'
import connect from '../../helpers/database'

// Initialize the cors middleware
const cors = initMiddleware(
  Cors({
    methods: ['POST', 'OPTIONS']
  })
)

export default async (
  req: NextApiRequest,
  res: NextApiResponse
): Promise<void> => {
  const { body, method } = req

  await cors(req, res)

  if (method === 'POST') {
    // create user in your database
    const { db } = await connect()

    const respDB = await db.collection('users').findOne(body)
    console.log(respDB)
    if (respDB !== null) {
      res.status(200).json({ message: 'Login success', data: respDB })
    } else {
      // login failed
      res.status(401).json({ message: 'Login failed' })
    }
  } else {
    res.setHeader('Allow', ['GET', 'PUT'])
    res.status(405).end(`Method ${method} Not Allowed`)
  }
}

// const passDB = 'BZacnM@FP7hziPU'
// const userDB = 'maykonjv'
// const hostDB = `mongodb+srv://${userDB}:${passDB}@localhost:27017/lowcodesystem?retryWrites=true&w=majority`
