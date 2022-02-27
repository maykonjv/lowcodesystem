import { NextApiRequest, NextApiResponse } from "next";
import Cors from "cors";
import initMiddleware from "../../helpers/init-middleware";
import { UserType } from "../../services/api";
import connect from "../../helpers/database";

// Initialize the cors middleware
const cors = initMiddleware(
  Cors({
    methods: ["GET", "POST", "PUT", "DELETE", "OPTIONS"],
  })
);

export default async function handler(
  req: NextApiRequest,
  res: NextApiResponse
) {
  const {
    query: { id },
    body,
    method,
  } = req;

  await cors(req, res);

  switch (method) {
    case "GET":
      // Get data from your database
      if (id) {
        const user = get(id as string);
        res.status(200).json(user);
      } else {
        const users = getAll();
        res.status(200).json(users);
      }
      break;
    case "PUT":
      // Update or create data in your database
      if (id) {
        const user = update(body as UserType);
        res.status(200).json(user);
      } else {
        const user = create(body as UserType);
        res.status(200).json(user);
      }
      break;
    case "POST":
      // Create data in your database
      const user = create(body as UserType);
      res.status(200).json(user);
      break;
    case "DELETE":
      // Delete data in your database
      if (id) {
        const user = remove(id as string);
        res.status(200).json(user);
      } else {
        res.status(400).json({ message: "Please provide an id" });
      }
      break;
    default:
      res.setHeader("Allow", ["GET", "PUT"]);
      res.status(405).end(`Method ${method} Not Allowed`);
  }
}

// create user in your database
const create = async (user: UserType) => {
  try {
    const { db } = await connect();

    const respDB = await db.collection("users").insertOne(user as any);
    return respDB.insertedId;
  } catch (e) {
    console.log(e);
    return null;
  }
};

// update user in your database
const update = async (user: UserType) => {
  try {
    const { db } = await connect();

    const respDB = await db
      .collection("users")
      .updateOne({ _id: user._id }, { $set: user });
    return respDB.modifiedCount;
  } catch (e) {
    console.log(e);
    return null;
  }
};

// delete user in your database
const remove = async (id: string) => {
  try {
    const { db } = await connect();

    const respDB = await db.collection("users").deleteOne({ _id: id });
    return respDB.deletedCount;
  } catch (e) {
    console.log(e);
    return null;
  }
};

// get user from your database
const get = async (id: string) => {
  try {
    const { db } = await connect();

    const respDB = await db
      .collection("users")
      .findOne({ _id: id }, { password: 0 } as any);
    return respDB;
  } catch (e) {
    console.log(e);
    return null;
  }
};

// get all users from your database
const getAll = async () => {
  try {
    const { db } = await connect();

    const respDB = await db
      .collection("users")
      .find({}, { password: 0 } as any)
      .toArray();
    return respDB;
  } catch (e) {
    console.log(e);
    return null;
  }
};
