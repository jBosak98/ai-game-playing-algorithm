import React from "react";
import { gameModeAiVsAi, gameModeAiVsPlayer, gameModePlayerVsPlayer } from '../lib/constants';

const getPlayerTurnType = (nextMove, gameMode) => {
  if(gameMode === gameModeAiVsAi) return 'Computer';
  if(gameMode === gameModeAiVsPlayer) return  nextMove === "BLACK" ? 'Computer' : "Player";
  if(gameMode === gameModePlayerVsPlayer) return "Player"
  return ""
}

const TurnInfo = ({nextMove, gameMode}) => {
  const playerType = getPlayerTurnType(nextMove, gameMode);
  return <>Next turn: {nextMove.toLowerCase()} ({playerType})</>
}

export default TurnInfo;