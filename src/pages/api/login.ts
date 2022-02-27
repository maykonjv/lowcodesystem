import { NextApiRequest, NextApiResponse } from "next";
import Cors from "cors";
import initMiddleware from "../../helpers/init-middleware";
import connect from "../../helpers/database";

// Initialize the cors middleware
const cors = initMiddleware(
  Cors({
    methods: ["POST", "OPTIONS"],
  })
);

export default async function handler(
  req: NextApiRequest,
  res: NextApiResponse
) {
  const { body, method } = req;

  await cors(req, res);

  if (method === "POST") {
    // create user in your database
    const { db } = await connect();

    const respDB = await db
      .collection("users")
      .findOne(body, { firstname: 1, lastname: 1, email: 1 } as any);
    console.log(respDB);
    if (respDB !== null) {
      res
        .status(200)
        .json({ message: "Login success", token: "123", user: respDB });
    } else {
      // login failed
      res.status(401).json({ message: "Login failed" });
    }
  } else {
    res.setHeader("Allow", ["GET", "PUT"]);
    res.status(405).end(`Method ${method} Not Allowed`);
  }
}
