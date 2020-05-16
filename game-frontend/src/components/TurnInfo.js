import React from "react";

import {
  gameModes,
  PLAYER_TYPE_COMPUTER,
  PLAYER_TYPE_PLAYER,
  COLOR_BLACK,
} from "../lib/constants";

const getPlayerTurnType = (nextMove, gameMode) => {
  const { aiVsAi, aiVsPlayer, playerVsPlayer } = gameModes;
  if (gameMode.mode === aiVsAi) return PLAYER_TYPE_COMPUTER;
  if (gameMode.mode === aiVsPlayer)
    return nextMove === COLOR_BLACK ? PLAYER_TYPE_COMPUTER : PLAYER_TYPE_PLAYER;
  if (gameMode.mode === playerVsPlayer) return PLAYER_TYPE_PLAYER;
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
