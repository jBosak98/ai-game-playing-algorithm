const sendConfig = (sendMessage, data, config) => {
  const message = {
    board: {
      config,
      ...data,
    },
  };
  sendMessage(JSON.stringify(message));
};

export default sendConfig;