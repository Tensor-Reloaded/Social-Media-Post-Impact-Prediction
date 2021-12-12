export function api<T>(url: string, method: string): Promise<T> {
    return fetch(url, {method: method, headers: {"Content-Type": "application/json"}})
      .then(response => {
        if (!response.ok) {
          throw new Error(response.statusText)
        }
        return response.json<T>();
      })
  }