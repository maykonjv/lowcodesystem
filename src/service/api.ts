const url = "http://localhost:3001/api/";

export const api = {
  get: (path: string) => {
    return fetch(url + path).then((res) => res.json());
  },
  post: (path: string, data: any) => {
    return fetch(url + path, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    }).then((res) => res.json());
  },
};
