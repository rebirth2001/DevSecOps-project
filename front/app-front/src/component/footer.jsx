function Footer() {
  return (
    <>
      <footer className="w-full bg-gray-900">
        {/* <div className="text-white p-4">
          <ul className="flex justify-start">
            <li className="mx-2">
              <a href="/">
                <span className="font-bold text-4xl">Quizzly</span>
              </a>
            </li>
            <li className="mx-2">
              <a href="/about-us">
                <span className="font-bold text-4xl">About Us</span>
              </a>
            </li>
          </ul>
        </div> */}
        <div className="text-white flex justify-center text-xs p-4">
          <p>
            Copyright &copy; <a href="/">Quizzly</a> All rights reserved.
          </p>
        </div>
      </footer>
    </>
  );
}

export default Footer;
