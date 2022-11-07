import { useAxios } from "./use-axios";

export function useHttp(baseURL, headers) {
  const instance = useAxios(baseURL, headers);

  async function get(url) {
    const response = await instance.get(url);

    return response.data;
  }

  async function post(url, data) {
    const response = await instance.post(url, data);

    return response.data;
  }

  async function put(url, data) {
    const response = await instance.put(url, data);

    return response.data;
  }

  async function delet(url, data) {
    const response = await instance.delete(url, data);

    return response.data;
  }

  return {
    get,
    post,
    put,
    delet,
  };
}
