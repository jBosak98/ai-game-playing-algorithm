const sendConfig = (sendMessage, data, config) => {
  const message = {
    board: {
      config,
      ...data,
    },
  };
  console.log(config)
  sendMessage(JSON.stringify(message));
};

export default sendConfig;