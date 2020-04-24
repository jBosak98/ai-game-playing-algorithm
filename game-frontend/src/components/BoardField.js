import React from "react";
import classNames from "classnames";
import "./BoardField.scss";

const BoardField = ({
  blackColor,
  onBoardFieldClick,
  rowNumber,
  columnNumber,
  clickedPawn,
}) => {
  const isClicked =
    clickedPawn &&
    clickedPawn.row === rowNumber &&
    clickedPawn.column === columnNumber;
  return (
    <div
      onClick={() => onBoardFieldClick({ rowNumber, columnNumber })}
      className={classNames("BoardField", { black: blackColor, isClicked })}
    ></div>
  );
};

export default BoardField;
