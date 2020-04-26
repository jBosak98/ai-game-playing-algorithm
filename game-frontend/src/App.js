import React from "react";
import useWebSocket, { ReadyState } from 'react-use-websocket';
import "./App.scss";
import Loader from './components/Loader';
import Board from "./components/Board";


// const pawns = [
//   { id: 0, row: 5, column: 3, color: "black" },
//   { id: 1, row: 2, column: 3, color: "white" },
// ];

function App() {
  const socketUrl = 'ws://0.0.0.0:8080/createGame';
  const [sendMessage, lastMessage, readyState, getWebSocket] = useWebSocket(socketUrl);
  printConnectionStatus(readyState);
  // console.log(lastMessage?.data);
  console.log(sendMessage('Hello'));
  const data = lastMessage && lastMessage.data && JSON.parse(lastMessage.data);
  console.log(data);
  if(!data){
    return <Loader/>
  }
  console.log(data)
  const pawns = [
    ...Object.entries(data.pawns).map(x =>x[1])
  ]
  console.log(pawns)
  return (
    <div className="App">
      <Board row={8} column={8} pawns={pawns} />
    </div>
  );
}


const printConnectionStatus = (readyState) => {
  const connectionStatus = {
    [ReadyState.CONNECTING]: 'Connecting',
    [ReadyState.OPEN]: 'Open',
    [ReadyState.CLOSING]: 'Closing',
    [ReadyState.CLOSED]: 'Closed',
  }[readyState];
  console.log("socket:", connectionStatus);
  return connectionStatus;
}
export default App;
