import { NextApiRequest, NextApiResponse } from 'next'
import Cors from 'cors'
import initMiddleware from '../../helpers/init-middleware'

// Initialize the cors middleware
const cors = initMiddleware(
  Cors({
    methods: ['GET', 'POST', 'OPTIONS']
  })
)

export default async (
  req: NextApiRequest,
  res: NextApiResponse
): Promise<void> => {
  const {
    query: { id, name },
    method
  } = req

  await cors(req, res)

  switch (method) {
    case 'GET':
      // Get data from your database
      res.status(200).json({ id, name: `User ${id}` })
      break
    case 'PUT':
      // Update or create data in your database
      res.status(200).json({ id, name: name || `User ${id}` })
      break
    default:
      res.setHeader('Allow', ['GET', 'PUT'])
      res.status(405).end(`Method ${method} Not Allowed`)
  }
}
