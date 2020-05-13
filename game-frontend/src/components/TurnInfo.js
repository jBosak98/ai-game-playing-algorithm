import React from "react";
import {
  gameModeAiVsAi,
  gameModeAiVsPlayer,
  gameModePlayerVsPlayer,
} from "../lib/constants";

const getPlayerTurnType = (nextMove, gameMode) => {
  if (gameMode === gameModeAiVsAi) return "Computer";
  if (gameMode === gameModeAiVsPlayer)
    return nextMove === "BLACK" ? "Computer" : "Player";
  if (gameMode === gameModePlayerVsPlayer) return "Player";
  return "";
};

const TurnInfo = ({ nextMove, gameMode, winner }) => {
  const playerType = getPlayerTurnType(nextMove, gameMode);
  if (winner) return <h1>WINNER: {winner} player</h1>;

  return (
    <>
      Next turn: {nextMove.toLowerCase()} ({playerType})
    </>
  );
};

export default TurnInfo;
