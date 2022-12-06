import { ServicesSchema } from '../config/schema/schema';
import axios from 'axios';

export const api = {} as any;

export function addApi(name: string, url: string) {
  api[name] = axios.create({
    baseURL: url,
  });
}

export function fetchApi(nameService: string, nameApi: string, params: any) {
  const apis = ServicesSchema.find((item) => item.name === nameService)?.apis.find((item) => item.name === nameApi);
  if (!apis) {
    return;
  }
  return api[nameService][apis.method.toLowerCase()](apis.path, params);
}
