// Make an HTTP GET request to the API endpoint
const apiKey = "AIzaSyCzxCtyEbt8-b6186kLT-nRpnTfwk3SZrk";
const cx = "b6f062ba4f2ae4513";
var query;

const url = `https://www.googleapis.com/customsearch/v1?key=${apiKey}&cx=${cx}&q=${encodeURIComponent(
  query
)}`;

query_list_json = [
  {
    category: "vegetable",
    food_identifier: "spinach",
    expiration_date: 1,
  },
  {
    category: "vegetable",
    food_identifier: "mustard greens",
    expiration_date: 1,
  },
  {
    category: "vegetable",
    food_identifier: "sweet potato",
    expiration_date: 6,
  },
  {
    category: "vegetable",
    food_identifier: "cucumber",
    expiration_date: 9,
  },
  {
    category: "vegetable",
    food_identifier: "corn",
    expiration_date: 15,
  },
  {
    category: "vegetable",
    food_identifier: "spinach",
    expiration_date: 5,
  },
  {
    category: "meat",
    food_identifier: "striploin",
    expiration_date: 30,
  },
  {
    category: "meat",
    food_identifier: "tenderloin",
    expiration_date: 1,
  },
  {
    category: "meat",
    food_identifier: "ribeye",
    expiration_date: 5,
  },
  {
    category: "meat",
    food_identifier: "steak",
    expiration_date: 7,
  },
  {
    category: "meat",
    food_identifier: "minced beef",
    expiration_date: 20,
  },

  {
    category: "meat",
    food_identifier: "mutton",
    expiration_date: 50,
  },
  {
    category: "meat",
    food_identifier: "lamb leg",
    expiration_date: 8,
  },
  {
    category: "meat",
    food_identifier: "loin",
    expiration_date: 13,
  },
  {
    category: "meat",
    food_identifier: "lamb shank",
    expiration_date: 24,
  },
  {
    category: "poultry",
    food_identifier: "chicken",
    expiration_date: 25,
  },
  {
    category: "poultry",
    food_identifier: "chicken wing",
    expiration_date: 1,
  },
  {
    category: "poultry",
    food_identifier: "chicken fillet",
    expiration_date: 8,
  },
  {
    category: "poultry",
    food_identifier: "chicken ribs",
    expiration_date: 20,
  },
  {
    category: "fruits",
    food_identifier: "watermelon",
    expiration_date: 30,
  },
  {
    category: "fruits",
    food_identifier: "orange",
    expiration_date: 7,
  },
  {
    category: "fruits",
    food_identifier: "pineapple",
    expiration_date: 16,
  },
  {
    category: "fruits",
    food_identifier: "mango",
    expiration_date: 8,
  },
  {
    category: "fruits",
    food_identifier: "blueberry",
    expiration_date: 32,
  },
  {
    category: "seafood",
    food_identifier: "pomfret",
    expiration_date: 40,
  },
  {
    category: "seafood",
    food_identifier: "sardine",
    expiration_date: 30,
  },
  {
    category: "seafood",
    food_identifier: "anchovy",
    expiration_date: 35,
  },
  {
    category: "seafood",
    food_identifier: "golden snapper",
    expiration_date: 2,
  },
  {
    category: "seafood",
    food_identifier: "stingray",
    expiration_date: 4,
  },
  {
    category: "eggs",
    food_identifier: "eggs",
    expiration_date: 1,
  },
  {
    category: "eggs",
    food_identifier: "salted eggs",
    expiration_date: 1,
  },
];
vegetable_list = [];
meat_list = [];
poultry_list = [];
fruits_list = [];
seafood_list = [];
eggs_list = [];
animal_list = [];
others_list = [];
var animal_list_priority;
var others_list_priority;

/*fetch(url)
  .then((response) => response.json())
  .then((data) => {
    // Handle the API response data
    console.log(data);
    //console.log(data.items[1].link);
  })
  .catch((error) => {
    // Handle any errors
    console.error(error);
  });*/

//sorting query list into arrays based on category
function query_list_sort() {
  return new Promise((resolve, reject) => {
    let arrayItems = query_list_json;
    for (let i = 0; i < arrayItems.length; i++) {
      if (arrayItems[i].category == "vegetable") {
        vegetable_list.push(arrayItems[i]);
      } else if (arrayItems[i].category == "meat") {
        meat_list.push(arrayItems[i]);
      } else if (arrayItems[i].category == "poultry") {
        poultry_list.push(arrayItems[i]);
      } else if (arrayItems[i].category == "fruits") {
        fruits_list.push(arrayItems[i]);
      } else if (arrayItems[i].category == "seafood") {
        seafood_list.push(arrayItems[i]);
      } else if (arrayItems[i].category == "eggs") {
        eggs_list.push(arrayItems[i]);
      }
      resolve();
    }
  });

  /*category
    .then(() => {
      tally();
      animal_list = meat_list.concat(poultry_list, seafood_list);
      console.log(animal_list);
      console.log("concated");
    })
    .then(() => {
      expiration;
    });*/
}

//ensure all list is accounted for
function tally() {
  return new Promise((resolve, reject) => {
    if (
      (query_list_json.length =
        vegetable_list.length +
        meat_list.length +
        poultry_list.length +
        fruits_list.length +
        seafood_list.length +
        eggs_list.length)
    ) {
      console.log("Array elements tally");
      console.log("Vegetable List");
      console.log(vegetable_list);
      console.log("Meat List");
      console.log(meat_list);
      console.log("Poultry List");
      console.log(poultry_list);
      console.log("fruits List");
      console.log(fruits_list);
      console.log("Seafood List");
      console.log(seafood_list);
      console.log("Eggs List");
      console.log(eggs_list);
      resolve();
    } else {
      console.log("Array elements not tally");
    }
  });
}

//sort objects based on expiration date in ascending order
function expiration() {
  return new Promise((resolve, reject) => {
    animal_list.sort((a, b) => {
      return a.expiration_date - b.expiration_date;
    });
    animal_list.forEach((obj) => {
      console.log(
        `${obj.food_identifier} ${obj.category} ${obj.expiration_date}`
      );
    });
    others_list.sort((a, b) => {
      return a.expiration_date - b.expiration_date;
    });
    others_list.forEach((obj) => {
      console.log(
        `${obj.food_identifier} ${obj.category} ${obj.expiration_date}`
      );
    });
    resolve();
  });
}

//prioritise objects to be used as query parameters, expiration within 1 week
function prioritise() {
  return new Promise((resolve, reject) => {
    animal_list_priority = animal_list.filter((obj) => obj.expiration_date < 8);
    others_list_priority = others_list.filter((obj) => obj.expiration_date < 8);
    animal_list_priority.forEach((obj) => {
      console.log(
        `${obj.food_identifier} ${obj.category} ${obj.expiration_date}`
      );
    });
    others_list_priority.forEach((obj) => {
      console.log(
        `${obj.food_identifier} ${obj.category} ${obj.expiration_date}`
      );
    });
    resolve();
  });
}

async function search_engine_processing() {
  query_list_sort()
    .then(() => {
      tally();
    })
    .then(() => {
      console.log("animal list");
      animal_list = meat_list.concat(poultry_list, seafood_list);
      console.log(animal_list);
      console.log("others list");
      others_list = vegetable_list.concat(fruits_list, eggs_list);
      console.log(others_list);
      console.log("concated");
    })
    .then(() => {
      expiration();
    })
    .then(() => {
      prioritise();
    });
}

search_engine_processing();
