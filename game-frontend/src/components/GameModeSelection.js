import React from "react";
import Button from '@material-ui/core/Button';
import { gameModeAiVsAi, gameModeAiVsPlayer, gameModePlayerVsPlayer } from '../lib/constants';

import './GameModeSelection.scss';

const GameModeSelection = ({ setGameMode }) => {
  return <div className="GameModeSelection">
    <Button 
      variant="contained" 
      onClick={()=>setGameMode(gameModeAiVsAi)}
      color="primary"
      type="secondary">
        AI VS AI
    </Button  >
    <Button 
      color="primary"
      onClick={()=>setGameMode(gameModeAiVsPlayer)}
      variant="contained"
      type="primary">
        AI VS PLAYER
    </Button>
    <Button 
      color="primary"
      onClick={()=>setGameMode(gameModePlayerVsPlayer)}
      variant="contained"
      type="primary">
        PLAYER VS PLAYER
    </Button>
  </div>
}

export default GameModeSelection;