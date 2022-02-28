var ObjectID = require('mongodb').ObjectID
import { hash } from '../../helpers/helper.crypto'
import connect from '../../helpers/helper.database'
import { UserType } from '../api'

// create user in your database
export const create = async (user: UserType) => {
  try {
    const { db } = await connect()
    user.created_at = new Date()
    const hashPass = await hash(user.password)
    user.password = hashPass
    const respDB = await db.collection('users').insertOne(user as any)
    return respDB.insertedId
  } catch (e: any) {
    console.log(`user.dao.ts create: ${e.name}: ${e.message}`)
    return null
  }
}

// update user in your database
export const update = async (user: UserType) => {
  try {
    const { db } = await connect()
    user.updated_at = new Date()
    await hash(user.password)
    const respDB = await db
      .collection('users')
      .updateOne({ _id: new ObjectID(user._id) }, { $set: user })
    return respDB.modifiedCount
  } catch (e: any) {
    console.log(`user.dao.ts update: ${e.name}: ${e.message}`)
    return null
  }
}

// delete user in your database
export const remove = async (id: string) => {
  try {
    const { db } = await connect()

    const respDB = await db
      .collection('users')
      .findOneAndUpdate({ _id: new ObjectID(id) }, { deleted_at: new Date() })
    return respDB.ok
  } catch (e: any) {
    console.log(`user.dao.ts remove: ${e.name}: ${e.message}`)
    return null
  }
}

// get user from your database
export const get = async (id: string) => {
  try {
    const { db } = await connect()
    const respDB = await db
      .collection('users')
      .findOne({ _id: new ObjectID(id) }, {
        projection: {
          password: 0
        }
      } as any)
    return respDB
  } catch (e: any) {
    console.log(`user.dao.ts get: ${e.name}: ${e.message}`)
    return null
  }
}

// get all users from your database
export const getAll = async () => {
  try {
    const { db } = await connect()

    const respDB = await db
      .collection('users')
      .find({ deleted_at: null }, { projection: { password: 0 } })
      .toArray()
    return respDB
  } catch (e: any) {
    console.log(`user.dao.ts getAll: ${e.name}: ${e.message}`)
    return null
  }
}
