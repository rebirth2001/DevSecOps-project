export class User {
  private accessToken: string;
  private username: string;
  private refreshToken: string;
  private expiration: number; // expect a value in ms
  private isExpired: boolean;

  constructor(
    accessToken: string,
    refreshToken: string,
    username: string,
    expiration: number
  ) {
    this.accessToken = accessToken;
    this.username = username;
    this.refreshToken = refreshToken;
    this.expiration = expiration;
    this.isExpired = false;
    setTimeout(this.setIsExpired.bind(this), this.expiration);
  }

  private setIsExpired() {
    console.log("User JWT token has expired!!!!!!!!!!!");
    this.isExpired = true;
  }

  public isValid(): boolean {
    console.log(this);
    return !this.isExpired;
  }

  public getUsername() {
    return this.username;
  }
}

export type UserProfile = {
  quizesCount: number;
  quizesTaken: number;
  username: string;
  email: string;
  joinedAt: string;
};
