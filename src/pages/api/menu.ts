import { NextApiRequest, NextApiResponse } from 'next'
import Cors from 'cors'
import initMiddleware from '../../helpers/init-middleware'
import { MenuType } from '../../services/api'
import connect from '../../helpers/helper.database'
import { hash, verifyToken } from '../../helpers/helper.crypto'

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
      if (!authorization || (!await verifyToken(authorization))) {
        res.status(401).json({ error: 'Unauthorized' })
      } else if (id) {
        const menu = get(id as string)
        res.status(200).json(menu)
      } else {
        const menus = getAll()
        res.status(200).json(menus)
      }
      break
    case 'PUT':
      // Update or create data in your database
      if (!authorization || (!await verifyToken(authorization))) {
        res.status(401).json({ error: 'Unauthorized' })
      } else if (id) {
        const menu = update(body as MenuType)
        res.status(200).json(menu)
      } else {
        const menu = create(body as MenuType)
        res.status(200).json(menu)
      }
      break
    case 'POST':
      // Create data in your database
      const menu = create(body as MenuType)
      res.status(200).json(menu)
      break
    case 'DELETE':
      // Delete data in your database
      if (!authorization || (!await verifyToken(authorization))) {
        res.status(401).json({ error: 'Unauthorized' })
      } else if (id) {
        const menu = remove(id as string)
        res.status(200).json(menu)
      } else {
        res.status(400).json({ message: 'Please provide an id' })
      }
      break
    default:
      res.setHeader('Allow', ['GET', 'PUT', 'POST', 'DELETE'])
      res.status(405).end(`Method ${method} Not Allowed`)
  }
}

// create menu in your database
const create = async (menu: MenuType) => {
  try {
    const { db } = await connect()
    menu.created_at = new Date()
    const respDB = await db.collection('menus').insertOne(menu as any)
    return respDB.insertedId
  } catch (e) {
    console.log(e)
    return null
  }
}

// update menu in your database
const update = async (menu: MenuType) => {
  try {
    const { db } = await connect()
    menu.updated_at = new Date()
    const respDB = await db
      .collection('menus')
      .updateOne({ _id: menu._id }, { $set: menu })
    return respDB.modifiedCount
  } catch (e) {
    console.log(e)
    return null
  }
}

// delete menu in your database
const remove = async (id: string) => {
  try {
    const { db } = await connect()

    const respDB = await db.collection('menus').deleteOne({ _id: id })
    return respDB.deletedCount
  } catch (e) {
    console.log(e)
    return null
  }
}

// get menu from your database
const get = async (id: string) => {
  try {
    const { db } = await connect()

    const respDB = await db.collection('menus').findOne({ _id: id }, {
      projection: {
        password: 0
      }
    } as any)
    return respDB
  } catch (e) {
    console.log(e)
    return null
  }
}

// get all menus from your database
const getAll = async () => {
  try {
    const { db } = await connect()

    const respDB = await db.collection('menus').find({}).toArray()
    return respDB
  } catch (e) {
    console.log(e)
    return null
  }
}
