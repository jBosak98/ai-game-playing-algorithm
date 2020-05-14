import React from "react";
import { gameModes } from "../lib/constants";

const getPlayerTurnType = (nextMove, gameMode) => {
  const { aiVsAi, aiVsPlayer, playerVsPlayer } = gameModes;
  if (gameMode === aiVsAi) return "Computer";
  if (gameMode === aiVsPlayer)
    return nextMove === "BLACK" ? "Computer" : "Player";
  if (gameMode === playerVsPlayer) return "Player";
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
