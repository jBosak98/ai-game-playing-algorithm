import React from "react";
import BoardRow from "./BoardRow";
import "./BoardBackground.scss";

const BoardBackground = ({ row, column, onBoardFieldClick, clickedPawn }) => {

  return (
    <div className="BoardBackground">
      {[...Array(row)].map((_, index) => (
        <BoardRow
          key={index}
          column={column}
          clickedPawn={clickedPawn}
          rowNumber={index}
          onBoardFieldClick={onBoardFieldClick}
        />
      ))}
    </div>
  );
};

export default BoardBackground;
