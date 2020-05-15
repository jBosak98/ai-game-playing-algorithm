import React from "react";

import { COLOR_BLACK } from "../lib/constants";
import CrownSVG from "../svg/CrownSVG";
import "./Pawn.scss";

const Pawn = ({ details, onPawnClick }) => {
  const style = getStyleFromPawnDetails(details);
  const isKing = details.isKing;
  return (
    <div className="Pawn" style={style} onClick={() => onPawnClick(details)}>
      <div
        className="fulfillment"
        style={{ backgroundColor: style.isBlack ? "#459" : "#bfa14d" }}
      >
        {isKing && <CrownSVG scale="0.3" transform="scale(0.5 0.5)" />}
      </div>
    </div>
  );
};

const getStyleFromPawnDetails = ({ row, column, team }) => {
  const fieldSizePx = 120;
  const isBlack = team === COLOR_BLACK;
  const top = `${row * fieldSizePx}px`;
  const left = `${column * fieldSizePx}px`;
  return { top, left, isBlack };
};

export default Pawn;
