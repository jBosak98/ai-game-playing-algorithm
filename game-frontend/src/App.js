import React from "react";
import "./App.scss";
import Board from "./components/Board";

function App() {  
  return (
    <div className="App">
      <Board row={8} column={8} />
    </div>
  );
}



export default App;
