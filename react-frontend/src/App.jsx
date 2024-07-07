import { useState } from "react";
import * as kotlin from "../../build/js/packages/kotlinlib/kotlin/kotlinlib.mjs"


function App() {
  console.log("==== start =====");
  let injector = kotlin.Injector.getInstance()
  let loginViewModel = injector.loginViewModel
  console.log(loginViewModel)
  console.log(Object.keys(loginViewModel));
  const { authToken_1 } = loginViewModel

  const [auth, setAuth] = useState(authToken_1._state)

  return (
    <>
      <div className="text-xl">Hello Ch8n!</div>
      <div className="text-xl">Token : {auth}</div>
      <button onClick={async () => {
        const token = await loginViewModel.login("ch8n", "ch8n@123")
        setAuth(token)
      }}>Login</button>
    </>
  )
}

export default App
