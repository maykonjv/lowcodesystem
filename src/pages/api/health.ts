import type { NextApiRequest, NextApiResponse } from "next";

//health check api
export default async function handler(
  req: NextApiRequest,
  res: NextApiResponse
) {
  res.status(200).json({ message: "ok" });
}
