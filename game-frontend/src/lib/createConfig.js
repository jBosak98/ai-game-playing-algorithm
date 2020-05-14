import { gameModes } from "./constants";


const createConfig = (gameMode) => {
  return { players: getPlayers(gameMode) };
};

const getPlayers = (gameMode) => {
  const { aiVsAi, aiVsPlayer, playerVsPlayer } = gameModes;

  const blackPlayerType = gameMode === playerVsPlayer ? "PLAYER" : "COMPUTER";
  const whitePlayerType = gameMode === aiVsAi ? "COMPUTER" : "PLAYER";

  const blackPlayer = {
    team: "BLACK",
    playerType: blackPlayerType,
    algorithmType: (blackPlayerType === "COMPUTER" && "ALPHA_BETA") || null,
    depth: (blackPlayerType === "COMPUTER" && 1) || null,
  };
  const whitePlayer = {
    team: "WHITE",
    playerType: whitePlayerType,
    algorithmType: (whitePlayerType === "COMPUTER" && "MIN_MAX") || null,
    depth: (whitePlayerType === "COMPUTER" && 1) || null,
  };
  console.log(blackPlayer);
  return [blackPlayer, whitePlayer];
};


export default createConfig;