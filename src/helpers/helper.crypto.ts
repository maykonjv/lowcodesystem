import { scrypt, randomBytes } from 'crypto'
const jwt = require('jsonwebtoken')

export async function hash(password: string): Promise<string> {
  return new Promise((resolve, reject) => {
    // generate random 16 bytes long salt
    const salt = randomBytes(16).toString('hex')

    scrypt(password, salt, 24, (err: any, derivedKey: any) => {
      if (err) reject(err)
      resolve(salt + ':' + derivedKey.toString('hex'))
    })
  })
}

export async function verify(password: string, hash: string): Promise<boolean> {
  return new Promise((resolve, reject) => {
    const [salt, key] = hash.split(':')
    scrypt(password, salt, 24, (err: any, derivedKey: any) => {
      if (err) reject(err)
      resolve(key == derivedKey.toString('hex'))
    })
  })
}

// genereate jwt token
export async function generateToken(id: string): Promise<string> {
  return await jwt.sign({ id }, process.env.JWT_SECRET || 'secret', {
    expiresIn: '1h'
  })
}

// verify jwt token
export async function verifyToken(token: string): Promise<boolean> {
  try{
    token = token.replace('Bearer ', '')
    const decoded = await jwt.verify(token, process.env.JWT_SECRET || 'secret')
    console.log('decoded', decoded)
    return true
  } catch(e: any) {
    console.log(`verifyToken error: ${e.name}: ${e.message}`)
    return false
  }
}
