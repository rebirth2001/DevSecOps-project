import LayoutMain from "../layouts/layout-main";
import Container from "../components/containter";
export default function Quiz() {
  return (
    <LayoutMain>
      <div
        aria-hidden="true"
        className="absolute inset-0 grid grid-cols-2 -space-x-52 opacity-40 dark:opacity-20"
      >
        <div className="blur-[106px] h-56 bg-gradient-to-br from-primary to-purple-400 dark:from-blue-700"></div>
        <div className="blur-[106px] h-32 bg-gradient-to-r from-cyan-400 to-sky-300 dark:to-indigo-600"></div>
      </div>
      <Container>
      <div className="relative pt-36 ml-auto flex justify-center py-12">
        <div className="mockup-browser border bg-base-300 ">
                <div className="mockup-browser-toolbar">
                    <div className="input">Question 1/20</div>
                </div>
                <div className="flex flex-col items-center">
                    <div className="mb-8 text-xl font-bold">Quelle est la couleur du ciel?</div>

                    <div className="form-control" >
                        <label className="label cursor-pointer">
                        <input
                            type="radio"
                            name="radio-qcm"
                            className="radio checked:bg-red-500"
                            checked
                        />
                        <span className="label-text ml-5">Rouge</span>
                        </label>
                    </div>

                    <div className="form-control" >
                        <label className="label cursor-pointer">
                        <input
                            type="radio"
                            name="radio-qcm"
                            className="radio checked:bg-blue-500"
                            checked
                        />
                        <span className="label-text ml-5">Bleu</span>
                        </label>
                    </div>

                    <div className="form-control" >
                        <label className="label cursor-pointer">
                        <input
                            type="radio"
                            name="radio-qcm"
                            className="radio checked:bg-green-500"
                            checked
                        />
                        <span className="label-text ml-5">Vert</span>
                        </label>
                    </div>

                    <div className="form-control" >
                        <label className="label cursor-pointer">
                        <input
                            type="radio"
                            name="radio-qcm"
                            className="radio checked:bg-yellow-500"
                            checked
                        />
                        <span className="label-text ml-5">Jaune</span>
                        </label>
                    </div>
                    </div>
                    <button
                  className=" w-full py-4 px-2 rounded bg-indigo-300 text-white hover:bg-indigo-700 font-bold text-lg mt-10"
                  type="submit"
                >
                  Submit
                </button>
            </div>
        </div>
      </Container>
    </LayoutMain>
  );
}


