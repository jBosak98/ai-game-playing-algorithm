const shouldComputerMove = (data) =>
  data?.config?.players.find(({ team }) => team === data.nextMove)
    .playerType === "COMPUTER";

export default shouldComputerMove;
