import { PLAYER_TYPE_COMPUTER } from "../lib/constants";


const shouldComputerMove = (data) =>
  data?.config?.players.find(({ team }) => team === data.nextMove)
    .playerType === PLAYER_TYPE_COMPUTER;

export default shouldComputerMove;
