import "../style/App.css"
import "../style/Obj.css"
import "../style/Custom.css"

import Page from "./page";
import { useState } from "react";
import { NAME } from "./Context";

function App() {

  return (
    <>
      <NAME.Provider value={useState(localStorage.getItem("name"))}>
        <Page/>
      </NAME.Provider>
    </>
  );
}

export default App;
