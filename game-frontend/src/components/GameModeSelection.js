import React from "react";
import Button from "@material-ui/core/Button";
import {
  gameModes,
  ALGORITHM_MIN_MAX,
  ALGORITHM_ALPHA_BETA,
} from "../lib/constants";
import Select from "@material-ui/core/Select";
import MenuItem from "@material-ui/core/MenuItem";
import TextField from "@material-ui/core/TextField";
import InputLabel from "@material-ui/core/InputLabel";
import Paper from "@material-ui/core/Paper";

import "./GameModeSelection.scss";

const GameModeSelection = ({ setGameMode, gameMode, start }) => {
  const { aiVsAi, aiVsPlayer, playerVsPlayer } = gameModes;
  return (
    <div className="GameModeSelection">
      {gameMode && gameMode.mode && (
        <DepthSelection
          setGameMode={setGameMode}
          gameMode={gameMode}
          start={start}
        />
      )}
      {!gameMode.mode && (
        <>
          <Button
            variant="contained"
            onClick={() => setGameMode({ mode: aiVsAi })}
            color="primary"
            type="secondary"
          >
            AI VS AI
          </Button>
          <Button
            color="primary"
            onClick={() => setGameMode({ mode: aiVsPlayer })}
            variant="contained"
            type="primary"
          >
            AI VS PLAYER
          </Button>
          <Button
            color="primary"
            onClick={() => setGameMode({ mode: playerVsPlayer }) || start(true)}
            variant="contained"
            type="primary"
          >
            PLAYER VS PLAYER
          </Button>
        </>
      )}
    </div>
  );
};

const DepthSelection = ({ gameMode, setGameMode, start }) => {
  const { aiVsAi } = gameModes;
  const numberOfComputerPlayers = (gameMode.mode === aiVsAi && 2) || 1;
  const setAlgorithm = (index) => (event) =>
    setGameMode({
      ...gameMode,
      [`player${index}`]: {
        ...gameMode[`player${index}`],
        algorithm: event.target.value,
      },
    });
  const getAlgorithm = (index) =>
    gameMode[`player${index}`] && gameMode[`player${index}`].algorithm;

  const setDepth = (index) => (event) =>
    setGameMode({
      ...gameMode,
      [`player${index}`]: {
        ...gameMode[`player${index}`],
        depth: event.target.value,
      },
    });
  const getDepth = (index) =>
    gameMode[`player${index}`] && gameMode[`player${index}`].depth;

  return (
    <Paper elevation={3}>
      {[...Array(numberOfComputerPlayers)].map((_, index) => (
        <div className="algorithmDetails">
          <InputLabel id="demo-simple-select-label">Algorithm</InputLabel>
          <Select
            labelId="demo-simple-select-label"
            id="demo-simple-select"
            value={getAlgorithm(index)}
            onChange={setAlgorithm(index)}
          >
            <MenuItem value={ALGORITHM_MIN_MAX}>MIN MAX algorithm</MenuItem>
            <MenuItem value={ALGORITHM_ALPHA_BETA}>
              ALPHA BETA algorithm
            </MenuItem>
          </Select>
          <TextField
            label="Depth"
            type="number"
            InputLabelProps={{
              shrink: true,
            }}
            value={getDepth(index)}
            onChange={setDepth(index)}
          />
        </div>
      ))}

      <Button
        color="primary"
        onClick={() =>
          isPoperlyFilled(gameMode, getAlgorithm, getDepth) && start(true)
        }
        variant="contained"
        type="primary"
      >
        Start
      </Button>
    </Paper>
  );
};

const isPoperlyFilled = (gameMode, getAlgorithm, getDepth) =>
  (gameMode.mode === gameModes.aiVsPlayer && getAlgorithm(0) && getDepth(0)) ||
  (gameMode.mode === gameModes.aiVsAi &&
    getAlgorithm(1) &&
    getAlgorithm(0) &&
    getDepth(0) &&
    getDepth(1));

export default GameModeSelection;
