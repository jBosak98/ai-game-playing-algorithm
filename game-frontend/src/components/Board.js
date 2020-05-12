import React, { useState } from "react";
import useWebSocket from 'react-use-websocket';
import Pawn from "./Pawn";
import BoardBackground from "./BoardBackground";
import "./Board.scss";
import Loader from './Loader';
import printConnectionStatus from '../lib/printConnectionStatus';
import { socketUrl } from '../lib/constants';
import TurnInfo from './TurnInfo';

const Board = ({ row, column, gameMode }) => {
  const [clickedPawn, onPawnClick] = useState(undefined);
  const [sendMessage, lastMessage, readyState, getWebSocket] = useWebSocket(socketUrl);
  
  printConnectionStatus(readyState);
  const data = parseData(lastMessage);
  
  if(!data) return <Loader/>
  

  const onBoardFieldClick = getOnBoardFieldClick({data, clickedPawn, sendMessage});
  const pawns = getPawns(data);
  
  const filteredOnPawnClick = (pawn) => pawn.team === data.nextMove && onPawnClick(pawn)
  
  return (
    <>
      <TurnInfo nextMove={data.nextMove} gameMode={gameMode}/>
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

const getOnBoardFieldClick = ({data, clickedPawn, sendMessage}) => (destination) => {
  const message = {
    board:data,
    move:{
      pawn:clickedPawn,
      destination
    }
  }
  sendMessage(JSON.stringify(message));
}

const parseData = (lastMessage) => lastMessage && lastMessage.data && JSON.parse(lastMessage.data);

const getPawns = (data) => [ ...Object.entries(data.pawns).map(x =>x[1])]

export default Board;
