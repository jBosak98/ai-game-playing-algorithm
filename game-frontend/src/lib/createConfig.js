import {
  gameModes,
  PLAYER_TYPE_COMPUTER,
  PLAYER_TYPE_PLAYER,
  COLOR_WHITE,
  COLOR_BLACK,
} from "./constants";

const createConfig = (gameMode) => {
  return { players: getPlayers(gameMode) };
};

const getPlayers = (gameMode) => {
  const { aiVsAi, aiVsPlayer, playerVsPlayer } = gameModes;

  const blackPlayerType =
    gameMode.mode === playerVsPlayer
      ? PLAYER_TYPE_PLAYER
      : PLAYER_TYPE_COMPUTER;
  const whitePlayerType =
    gameMode.mode === aiVsAi ? PLAYER_TYPE_COMPUTER : PLAYER_TYPE_PLAYER;

  const blackPlayer = {
    team: COLOR_BLACK,
    playerType: blackPlayerType,
    algorithmType: gameMode?.player0?.algorithm || null,
    depth: gameMode?.player0?.depth || null,
  };
  const whitePlayer = {
    team: COLOR_WHITE,
    playerType: whitePlayerType,
    algorithmType: gameMode?.player1?.algorithm || null,
    depth: gameMode?.player1?.depth || null,
  };
  return [blackPlayer, whitePlayer];
};

export default createConfig;
