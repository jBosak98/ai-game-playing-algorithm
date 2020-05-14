import React from "react";
import Button from "@material-ui/core/Button";
import { gameModes } from "../lib/constants";

import "./GameModeSelection.scss";

const GameModeSelection = ({ setGameMode }) => {
  const { aiVsAi, aiVsPlayer, playerVsPlayer } = gameModes;
  return (
    <div className="GameModeSelection">
      <Button
        variant="contained"
        onClick={() => setGameMode(aiVsAi)}
        color="primary"
        type="secondary"
      >
        AI VS AI
      </Button>
      <Button
        color="primary"
        onClick={() => setGameMode(aiVsPlayer)}
        variant="contained"
        type="primary"
      >
        AI VS PLAYER
      </Button>
      <Button
        color="primary"
        onClick={() => setGameMode(playerVsPlayer)}
        variant="contained"
        type="primary"
      >
        PLAYER VS PLAYER
      </Button>
    </div>
  );
};

export default GameModeSelection;
