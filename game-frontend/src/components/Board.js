import React, { useState } from "react";
import "./Board.scss";
import BoardBackground from "./BoardBackground";
import Loader from "./Loader";
import Pawn from "./Pawn";
import TurnInfo from "./TurnInfo";
import createConfig from "../lib/createConfig";
import makeMove from "../actions/makeMove";
import useCheckers from "../hooks/useCheckers";
import { socketUrl } from "../lib/constants";
import shouldComputerMove from "../lib/shouldComputerMove";

const Board = ({ row, column, gameMode }) => {
  const [clickedPawn, onPawnClick] = useState(undefined);
  const config = createConfig(gameMode);
  const [sendMessage, data, winner] = useCheckers(socketUrl, false, config);
  if (!data) return <Loader />;
  const isComputerMove = shouldComputerMove(data);

  const onBoardFieldClick =
    (!isComputerMove &&
      makeMove({
        data,
        clickedPawn,
        sendMessage,
      })) ||
    (() => {});
    
  const pawns = getPawns(data);

  const filteredOnPawnClick = (pawn) =>
    pawn.team === data.nextMove && onPawnClick(pawn);

  return (
    <>
      <TurnInfo nextMove={data.nextMove} gameMode={gameMode} winner={winner} />
      <div className="Board">
        <BoardBackground
          row={row}
          column={column}
          clickedPawn={clickedPawn}
          onBoardFieldClick={onBoardFieldClick}
        />
        {pawns.map((pawn, index) => (
          <Pawn details={pawn} onPawnClick={filteredOnPawnClick} key={index} />
        ))}
      </div>
    </>
  );
};

const getPawns = (data) => [...Object.entries(data.pawns).map((x) => x[1])];

export default Board;
