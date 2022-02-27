import { Db, MongoClient } from 'mongodb'

interface ConnectType {
  db: Db
  client: MongoClient
}

const client = new MongoClient(process.env.DATABASE_URL)

const connect = async (): Promise<ConnectType> => {
  try {
    console.log('Connecting to database...')
    await client.connect()
    const db = client.db(process.env.DATABASE_NAME)

    return { db, client }
  } catch (error) {
    console.error(error)
  }
}

export default connect
