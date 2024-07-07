import * as kotlin from "../../build/js/packages/kotlinlib/kotlin/kotlinlib.mjs"


function App() {
  console.log("==== start =====");
  let injector = kotlin.Injector.getInstance()
  let loginViewModel = injector.loginViewModel
  console.log(loginViewModel)
  const {authToken} = loginViewModel
  console.log(authToken);
  

  return (
    <>
      <div className="text-xl">Hello Ch8n!</div>
    </>
  )
}

export default App
