import { useState } from "react";
import useWebSocket from "react-use-websocket";

import shouldComputerMove from "../lib/shouldComputerMove";
import printConnectionStatus from "../lib/printConnectionStatus";
import makeMove from "../actions/makeMove";
import sendConfig from "../actions/sendConfig";

const onMessage = ({
  isWaitingForResponse,
  config,
  setIsWaitingForResponse,
  winner,
}) => (sendFunction) => (message) => {
  setIsWaitingForResponse(false);
  const sendMessage = getSendMessageFunction({
    winner,
    isWaitingForResponse,
    sendFunction,
  });
  const data = parseData(message);

  if (shouldAskAboutConfig(data, isWaitingForResponse))
    return sendConfig(sendMessage, data, config);
  if (shouldMakeComputerMove(data, isWaitingForResponse))
    return makeMove({ data, clickedPawn: null, sendMessage })({});
};

const useCheckers = (socketUrl, printStatus, config) => {
  const [isWaitingForResponse, setIsWaitingForResponse] = useState(false);
  const [winner, setWinner] = useState(undefined);
  const onMessageFunction = onMessage({
    isWaitingForResponse,
    config,
    setIsWaitingForResponse,
    winner,
  });
  const [sendFunction, lastMessage, readyState] = useWebSocket(socketUrl, {
    onOpen: () => console.log("onOpen"),
    onMessage: (message) => onMessageFunction(sendFunction)(message),
    enforceStaticOptions: false,
  });

  const data = parseData(lastMessage);
  printStatus && printConnectionStatus(readyState);

  !winner && data?.isFinished && data.winner && setWinner(data.winner);

  const sendMessage = getSendMessageFunction({
    winner,
    isWaitingForResponse,
    sendFunction
  });
  return [sendMessage, data, winner];
};



const parseData = (lastMessage) =>
  lastMessage && lastMessage.data && JSON.parse(lastMessage.data);

const shouldAskAboutConfig = (data, isWaitingForResponse) =>
  !data.config && !isWaitingForResponse;

const shouldMakeComputerMove = (data, isWaitingForResponse) =>
  shouldComputerMove(data) &&
  !isWaitingForResponse &&
  !data.isFinished &&
  data.config;

const getSendMessageFunction = ({
  winner,
  isWaitingForResponse,
  sendFunction
}) =>
  (!winner && !isWaitingForResponse && sendFunction) ||
  (() => undefined);

export default useCheckers;
