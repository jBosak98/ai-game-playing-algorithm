import { useState } from "react";
import useWebSocket from "react-use-websocket";

import shouldComputerMove from "../lib/shouldComputerMove";
import printConnectionStatus from "../lib/printConnectionStatus";

const useCheckers = (socketUrl, printStatus) => {
  const [sendMessage, lastMessage, readyState] = useWebSocket(socketUrl);
  const [winner, setWinner] = useState(undefined);
  const data = parseData(lastMessage);
  printStatus && printConnectionStatus(readyState);

  const isComputerMove = shouldComputerMove(data);
  !winner && data?.isFinished && data.winner && setWinner(data.winner);

  return [sendMessage, data, isComputerMove, winner];
};

const parseData = (lastMessage) =>
  lastMessage && lastMessage.data && JSON.parse(lastMessage.data);

export default useCheckers;
