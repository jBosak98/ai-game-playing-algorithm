import React, { useState } from "react";
import Pawn from "./Pawn";
import BoardBackground from "./BoardBackground";
import "./Board.scss";

const Board = ({ row, column, pawns = [] }) => {
  const [clickedPawn, onPawnClick] = useState(undefined);

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
