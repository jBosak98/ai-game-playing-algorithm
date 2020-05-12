import { ReadyState } from 'react-use-websocket';

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

export default printConnectionStatus;