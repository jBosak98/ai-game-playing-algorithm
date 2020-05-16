import React, { useState } from "react";
import "./App.scss";
import Board from "./components/Board";
import GameModeSelection from './components/GameModeSelection';

function App() {  
  const [gameMode, setGameMode] = useState({});
  const [isStarted, start] = useState(false);

  if(!gameMode.mode || !isStarted){
    return <GameModeSelection start={start} setGameMode={setGameMode} gameMode={gameMode}/>
  }

  return (
    <div className="App">
      <Board row={8} column={8} gameMode={gameMode} />
    </div>
  );
}



export default App;
