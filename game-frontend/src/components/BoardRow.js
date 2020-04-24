import React from "react";
import BoardField from "./BoardField";

const BoardRow = ({ rowNumber, column, onBoardFieldClick, clickedPawn }) => {
  return (
    <>
      {[...Array(column)].map((_, index) => (
        <BoardField
          key={index}
          onBoardFieldClick={onBoardFieldClick}
          blackColor={(index + rowNumber) % 2}
          rowNumber={rowNumber}
          columnNumber={index}
          clickedPawn={clickedPawn}
        />
      ))}
    </>
  );
};

export default BoardRow;
