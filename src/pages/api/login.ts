import { NextApiRequest, NextApiResponse } from 'next'
import Cors from 'cors'
import initMiddleware from '../../helpers/init-middleware'
import connect from '../../helpers/helper.database'
import { generateToken, verify } from '../../helpers/helper.crypto'

// Initialize the cors middleware
const cors = initMiddleware(
  Cors({
    methods: ['POST', 'OPTIONS']
  })
)

export default async function handler(
  req: NextApiRequest,
  res: NextApiResponse
) {
  const { body, method } = req

  await cors(req, res)

  if (method === 'POST') {
    // create user in your database
    const { db } = await connect()

    const userDB = await db
      .collection('users')
      .findOne({ email: body.email, deleted_at: null })
    console.log('userDB', userDB)
    if (userDB !== null && (await verify(body.password, userDB.password))) {
      userDB.password = ''
      const token = await generateToken(userDB._id.toString())
      res.status(200).json({ token, user: userDB })
    } else {
      // login failed
      res.status(401).json({ message: 'Verify your credentials and try again' })
    }
  } else {
    res.setHeader('Allow', ['POST'])
    res.status(405).end(`Method ${method} Not Allowed`)
  }
}
