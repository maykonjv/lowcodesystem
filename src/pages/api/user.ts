import { NextApiRequest, NextApiResponse } from 'next'
import Cors from 'cors'
import initMiddleware from '../../helpers/init-middleware'
import { UserType } from '../../services/api'
import { verifyToken } from '../../helpers/helper.crypto'
import * as userDao from '../../services/dao/user.dao'

// Initialize the cors middleware
const cors = initMiddleware(
  Cors({
    methods: ['GET', 'POST', 'PUT', 'DELETE', 'OPTIONS']
  })
)

export default async function handler(
  req: NextApiRequest,
  res: NextApiResponse
) {
  const {
    query: { id },
    body,
    method,
    headers: { authorization }
  } = req

  await cors(req, res)

  switch (method) {
    case 'GET':
      // Get data from your database
      if (!authorization || !(await verifyToken(authorization))) {
        res.status(401).json({ error: 'Unauthorized' })
      } else if (id) {
        const user = await userDao.get(id as string)
        res.status(200).json(user)
      } else {
        const users = await userDao.getAll()
        res.status(200).json(users)
      }
      break
    case 'PUT':
      // Update or create data in your database
      if (!authorization || !(await verifyToken(authorization))) {
        res.status(401).json({ error: 'Unauthorized' })
      } else if (id) {
        const user = await userDao.update(body as UserType)
        res.status(200).json(user)
      } else {
        const user = await userDao.create(body as UserType)
        res.status(200).json(user)
      }
      break
    case 'POST':
      // Create data in your database
      const user = await userDao.create(body as UserType)
      res.status(200).json(user)
      break
    case 'DELETE':
      // Delete data in your database
      if (!authorization || !(await verifyToken(authorization))) {
        res.status(401).json({ error: 'Unauthorized' })
      } else if (id) {
        const user = await userDao.remove(id as string)
        res.status(200).json(user)
      } else {
        res.status(400).json({ message: 'Please provide an id' })
      }
      break
    default:
      res.setHeader('Allow', ['GET', 'PUT', 'POST', 'DELETE'])
      res.status(405).end(`Method ${method} Not Allowed`)
  }
}
