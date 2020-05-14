
const makeMove = ({ data, clickedPawn, sendMessage }) => (
  destination
) => {
  const message = {
    board: data,
    move: {
      pawn: clickedPawn,
      destination: {
        row: destination.rowNumber,
        column: destination.columnNumber,
      },
    },
  };
  sendMessage(JSON.stringify(message));
};

export default makeMove;