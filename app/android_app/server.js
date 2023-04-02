const express = require("express");
const app = express();
const http = require("http");
const {
  MongoDB_ecommerce,
  MongoDB_transaction,
  MongoDB_getData,
} = require("./inventory_logic_API.js");

app.get("/", (req, res) => {
  res.send("Hello from App Engine!");
});

// Listen to the App Engine-specified port, or 8080 otherwise
/*const PORT = process.env.PORT || 8080;
app.listen(PORT, () => {
  console.log(`Server listening on port ${PORT}...`);
}); */

/* MongoDB_transaction(8292550568944);
setTimeout(() => {
  MongoDB_getData();
}, 2000);

setTimeout(() => {
  global.returnArray = JSON.stringify(global.sampleListTwo);
  console.log(global.returnArray);
}, 3500); */

function dataProcessing(data) {
  MongoDB_transaction(data);
  setTimeout(() => {
    MongoDB_getData();
  }, 1000);

  setTimeout(() => {
    global.returnArray = JSON.stringify(global.sampleListTwo);
    console.log(global.returnArray);
  }, 2000);

  setTimeout(() => {
    return { message: global.returnArray };
  }, 2250);
}

const server = http.createServer((req, res) => {
  if (req.method === "POST" && req.url === "/process") {
    let body = "";
    req.on("data", (chunk) => {
      body += chunk.toString();
    });
    req.on("end", () => {
      const result = dataProcessing(body);
      res.setHeader("Content-Type", "application/json");
      res.end(result);
    });
  } else {
    res.statusCode = 404;
    res.end();
  }
});

const PORT = 8080;
server.listen(PORT, () => {
  console.log(`Server is running at http://localhost:${PORT}.`);
});

//dataProcessing(4981829262400);
