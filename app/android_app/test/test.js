const chai = require("chai");
const expect = chai.expect;
const fetch = require("node-fetch");

describe("POST /process", function () {
  it("returns a JSON response with the correct full transaction details in JSON format", async function () {
    const response = await fetch("http://localhost:8080/process", {
      method: "POST",
      headers: { "Content-Rype": "application/json" },
      body: JSON.stringify({ input: 4981829262400 }),
    });

    expect(response.ok).toBe(true);

    const data = await response.json();
    expect(data.result).to.equal([
      {
        product_id: 1012,
        product_name: "MUTTON CUBES 1KG",
        identifier: "mutton",
        quantity: 4,
        expiration_date: "19/4/2023",
      },
      {
        product_id: 1010,
        product_name: "MINCE BEEF 500G",
        identifier: "minced beef",
        quantity: 2,
        expiration_date: "16/4/2023",
      },
      {
        product_id: 1016,
        product_name: "ORGANIC WHOLE CHICKEN 1KG",
        identifier: "chicken",
        quantity: 2,
        expiration_date: "24/4/2023",
      },
      {
        product_id: 1020,
        product_name: "CHICKEN RIBS 1KG",
        identifier: "chicken ribs",
        quantity: 2,
        expiration_date: "28/4/2023",
      },
      {
        product_id: 1005,
        product_name: "CORN 250GM",
        identifier: "corn",
        quantity: 5,
        expiration_date: "28/4/2023",
      },
    ]);
  });
});
