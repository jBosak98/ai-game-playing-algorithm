import React from "react";

import { COLOR_BLACK } from "../lib/constants";

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

const getStyleFromPawnDetails = ({ row, column, color, team }) => {
  const fieldSizePx = 120;
  const isBlack = team === COLOR_BLACK;
  const top = `${row * fieldSizePx}px`;
  const left = `${column * fieldSizePx}px`;
  return { top, left, isBlack };
};

export default Pawn;
