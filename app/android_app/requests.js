const http = require("http");

// Define the request parameters
const options = {
  method: "POST",
  hostname: "http://localhost",
  port: 8080,
  path: "/process",
  headers: {
    "Content-Type": "application/json",
  },
};

// Create the request
const req = http.request(options, (res) => {
  console.log(`Status: ${res.statusCode}`);
  console.log(`Headers: ${JSON.stringify(res.headers)}`);
  res.setEncoding("utf8");
  res.on("data", (chunk) => {
    console.log(`Body: ${chunk}`);
  });
  res.on("end", () => {
    console.log("No more data in response.");
  });
});

// Handle errors
req.on("error", (e) => {
  console.error(`Problem with request: ${e.message}`);
});

// Write the request body data
req.write("8292550568944");

// End the request
req.end();
