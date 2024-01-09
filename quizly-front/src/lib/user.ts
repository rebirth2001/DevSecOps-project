export class User {
  private username: string;
  private expiration: number; // expect a value in ms
  private isExpired: boolean;

  constructor(username: string, expiration: number) {
    this.username = username;
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
  quizsCreated: number;
  quizsTaken: number;
  username: string;
  email: string;
  joinedAt: string;
  followersCount: number;
};
