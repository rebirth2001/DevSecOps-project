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
      headers:{
        'Content-Type':'application/json',
      },
      body: JSON.stringify(rr),
    });
    return resp.ok;
  } catch {
    return false;
  }
}
