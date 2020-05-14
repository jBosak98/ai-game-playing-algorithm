import { gameModes } from "./constants";

const sendMessageWithConfig = (sendMessage, data, gameMode) => {
  const message = {
    board: {
      config: {
        players: getPlayers(gameMode),
      },
      ...data,
    },
  };
  sendMessage(JSON.stringify(message));
};

const getPlayers = (gameMode) => {
  const { aiVsAi, aiVsPlayer, playerVsPlayer } = gameModes;

  const blackPlayerType = gameMode === playerVsPlayer ? "PLAYER" : "COMPUTER";
  const whitePlayerType = gameMode === aiVsAi ? "COMPUTER" : "PLAYER";

  const blackPlayer = {
    team: "BLACK",
    playerType: blackPlayerType,
    algorithmType: blackPlayerType === "COMPUTER" && "ALPHA_BETA",
    depth: blackPlayerType === "COMPUTER" && 1,
  };
  const whitePlayer = {
    team: "WHITE",
    playerType: whitePlayerType,
    algorithmType: whitePlayerType === "COMPUTER" && "MIN_MAX",
    depth: whitePlayerType === "COMPUTER" && 1,
  };
  console.log(blackPlayer);
  return [blackPlayer, whitePlayer];
};

export default sendMessageWithConfig;
