import React from "react";

import "./Pawn.scss";

const Pawn = ({ details, onPawnClick }) => {
  const style = getStyleFromPawnDetails(details);

  return (
    <div className="Pawn" style={style} onClick={() => onPawnClick(details)}>
      <div
        className="fulfillment"
        style={{ backgroundColor: style.isBlack ? "#459" : "#bfa14d" }}
      ></div>
    </div>
  );
};

const getStyleFromPawnDetails = ({ row, column, color, isBlackTeam }) => {
  const fieldSizePx = 120;
  const isBlack = isBlackTeam;
  const top = `${row * fieldSizePx}px`;
  const left = `${column * fieldSizePx}px`;
  return { top, left, isBlack };
};

export default Pawn;
