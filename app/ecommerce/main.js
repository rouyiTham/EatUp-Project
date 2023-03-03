//import React from "react";
//import ReactDOM from "react-dom";
//import App from "./App.jsx";
const http = require("http");
const port = 3000;

const server = http.createServer(function (req, res) {});

server.listen(port, function (error) {
  if (error) {
    console.log(`Something went wrong: ${error}`);
  } else {
    console.log(`Server is listening on port ${port}`);
  }
});

//ReactDOM.render(<App />, document.getElementById("App"));
