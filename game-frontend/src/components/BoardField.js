import React from "react";
import classNames from "classnames";
import "./BoardField.scss";

const BoardField = ({
  blackColor,
  onBoardFieldClick,
  rowNumber,
  columnNumber,
  isPossibleMoveHighlight,
  isClicked
}) => {

  return (
    <div
      onClick={() => onBoardFieldClick({ rowNumber, columnNumber })}
      className={classNames("BoardField", { black: blackColor, isClicked, isPossibleMoveHighlight })}
    ></div>
  );
};

export default BoardField;
