const makeMove = ({ data, clickedPawn, sendMessage }) => (destination) => {
  const move = {
    pawn: clickedPawn,
    destination: {
      row: destination.rowNumber,
      column: destination.columnNumber,
    },
  };
  const message = {
    board: data,
    move: (clickedPawn && destination && move) || null,
  };
  
  sendMessage(JSON.stringify(message));
};

export default makeMove;
