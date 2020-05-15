import {
  gameModes,
  PLAYER_TYPE_COMPUTER,
  PLAYER_TYPE_PLAYER,
  ALGORITHM_MIN_MAX,
  ALGORITHM_ALPHA_BETA,
  COLOR_WHITE,
  COLOR_BLACK
} from "./constants";

const createConfig = (gameMode) => {
  return { players: getPlayers(gameMode) };
};

const getPlayers = (gameMode) => {
  const { aiVsAi, aiVsPlayer, playerVsPlayer } = gameModes;

  const blackPlayerType =
    gameMode === playerVsPlayer ? PLAYER_TYPE_PLAYER : PLAYER_TYPE_COMPUTER;
  const whitePlayerType =
    gameMode === aiVsAi ? PLAYER_TYPE_COMPUTER : PLAYER_TYPE_PLAYER;

  const blackPlayer = {
    team: COLOR_BLACK,
    playerType: blackPlayerType,
    algorithmType:
      (blackPlayerType === PLAYER_TYPE_COMPUTER && ALGORITHM_ALPHA_BETA) ||
      null,
    depth: (blackPlayerType === PLAYER_TYPE_COMPUTER && 1) || null,
  };
  const whitePlayer = {
    team: COLOR_WHITE,
    playerType: whitePlayerType,
    algorithmType:
      (whitePlayerType === PLAYER_TYPE_COMPUTER && ALGORITHM_MIN_MAX) || null,
    depth: (whitePlayerType === PLAYER_TYPE_COMPUTER && 1) || null,
  };
  return [blackPlayer, whitePlayer];
};

export default createConfig;
