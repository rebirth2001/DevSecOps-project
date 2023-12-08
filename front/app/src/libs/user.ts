import { API_BASE } from "./backend.binding";

export type RegisterRequest = {
  username: string;
  email: string;
  password: string;
  confirmPassword: string;
};
export async function RegisterUser(rr: RegisterRequest): Promise<boolean> {
  const REGISTER_ENDPOINT = "/auth/sign-up";
  const url = new URL(`${API_BASE}${REGISTER_ENDPOINT}`);
  try {
    const resp = await fetch(url, {
      method: "POST",
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(rr),
    });
    const json = await resp.json();
    
    // Check if the response has errors
    if (json.errors && json.errors.length > 0) {
      console.log(json.errors[0]) ;
    }
    return resp.ok;
  } catch {
    return false;
  }
}
