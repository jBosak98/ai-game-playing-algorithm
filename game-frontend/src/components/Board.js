import React, { useState } from "react";
import useWebSocket from "react-use-websocket";
import Pawn from "./Pawn";
import BoardBackground from "./BoardBackground";
import "./Board.scss";
import Loader from "./Loader";
import printConnectionStatus from "../lib/printConnectionStatus";
import { socketUrl } from "../lib/constants";
import TurnInfo from "./TurnInfo";
import sendConfig from "../actions/sendConfig";
import shouldComputerMove from '../lib/shouldComputerMove';
import makeMove from '../actions/makeMove';
import createConfig from '../lib/createConfig';

const Board = ({ row, column, gameMode }) => {
  const [clickedPawn, onPawnClick] = useState(undefined);
  const [winner, setWinner] = useState(undefined);
  const [sendMessage, lastMessage, readyState, getWebSocket] = useWebSocket(
    socketUrl
  );

  printConnectionStatus(readyState);
  const data = parseData(lastMessage);

  if (!data) return <Loader />;
  if (!data.config) sendConfig(sendMessage, data, createConfig(gameMode));

  const isComputerMove = shouldComputerMove(data);
  const onBoardFieldClick =
    (!isComputerMove &&
      makeMove({
        data,
        clickedPawn,
        sendMessage,
      })) ||
    (() => {});
  const pawns = getPawns(data);

  const filteredOnPawnClick = (pawn) =>
    pawn.team === data.nextMove && onPawnClick(pawn);

  !winner && data.isFinished && data.winner && setWinner(data.winner);

  return (
    <>
      <TurnInfo nextMove={data.nextMove} gameMode={gameMode} winner={winner} />
      <div className="Board">
        <BoardBackground
          row={row}
          column={column}
          clickedPawn={clickedPawn}
          onBoardFieldClick={onBoardFieldClick}
        />
        {pawns.map((pawn, index) => (
          <Pawn details={pawn} onPawnClick={filteredOnPawnClick} key={index} />
        ))}
      </div>
    </>
  );
};


const parseData = (lastMessage) =>
  lastMessage && lastMessage.data && JSON.parse(lastMessage.data);

const getPawns = (data) => [...Object.entries(data.pawns).map((x) => x[1])];

export default Board;
