import React, { useState } from "react";
import useWebSocket, { ReadyState } from 'react-use-websocket';
import Pawn from "./Pawn";
import BoardBackground from "./BoardBackground";
import "./Board.scss";
import Loader from './Loader';
import printConnectionStatus from '../lib/printConnectionStatus';
import { socketUrl } from '../lib/constants';

const Board = ({ row, column }) => {
  const [clickedPawn, onPawnClick] = useState(undefined);

  const [sendMessage, lastMessage, readyState, getWebSocket] = useWebSocket(socketUrl);
  printConnectionStatus(readyState);
  const data = lastMessage && lastMessage.data && JSON.parse(lastMessage.data);
  if(!data){
    return <Loader/>
  }
  const pawns = [
    ...Object.entries(data.pawns).map(x =>x[1])
  ]
  return (
    <div className="Board">
      <BoardBackground
        row={row}
        column={column}
        clickedPawn={clickedPawn}
        onBoardFieldClick={(id) => {
          console.log(clickedPawn, id);
        }}
      />
      {pawns.map((p, index) => (
        <Pawn details={p} onPawnClick={onPawnClick} key={index} />
      ))}
    </div>
  );
};



export default Board;
