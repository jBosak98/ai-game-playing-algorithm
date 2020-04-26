import React from "react";
import BoardField from "./BoardField";

const BoardRow = ({ rowNumber, column, onBoardFieldClick, clickedPawn }) => {
  const isClicked = (columnNumber) =>
    clickedPawn &&
    clickedPawn.row === rowNumber &&
    clickedPawn.column === columnNumber;
  const isPossibleMoveHighlight = (columnNumber) => 
    clickedPawn && 
    clickedPawn.possibleMoves &&
    clickedPawn.possibleMoves.find(
      (move) => move.row === rowNumber && move.column === columnNumber
      )
  return (
    <>
      {[...Array(column)].map((_, index) => (
        <BoardField
          key={index}
          onBoardFieldClick={onBoardFieldClick}
          blackColor={(index + rowNumber) % 2}
          rowNumber={rowNumber}
          isPossibleMoveHighlight={isPossibleMoveHighlight(index)}
          columnNumber={index}
          isClicked={isClicked(index)}
          clickedPawn={clickedPawn}
        />
      ))}
    </>
  );
};

export default BoardRow;
