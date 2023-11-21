export default function SignInForm() {
  return (
    <div className="hero min-h-screen ">
      <div className="hero-content flex-col lg:flex-row-reverse">
        <div className="card w-full shrink-0 w-full max-w-sm shadow-2xl bg-white ">
          <form className="card-body">
            <div className="form-control">
              <label className="label">
                <span className="label-text text-black">Email</span>
              </label>
              <input
                type="email"
                placeholder="email"
                className="input input-bordered"
                required
              />
            </div>
            <div className="form-control">
              <label className="label">
                <span className="label-text text-black">Password</span>
              </label>
              <input
                type="password"
                placeholder="password"
                className="input input-bordered"
                required
              />
              <div className="mt-4 flex">
              <label className="label ">
                <a href="#" className="label-text-alt link link-hover text-black">
                  Forgot password?
                </a>
              </label>
              <label className="label">
                <a href="/sign-in" className="label-text-alt link link-hover text-black">
                  Already have an account?
                </a>
              </label>
              </div>
            </div>
            <div className="form-control mt-6">
              <button className="btn btn-primary">Sign In</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}
