const api = require("../controllers/card-controller");

module.exports = (app) => {
    // api endpoint
    app.route("/cards")
    .post(api.save)
    .get(api.findAll)
    
    // api endpoint ordered
    app.route("/cards/paginationAndSorting")
    .get(api.findAllPage)

    // api enpoint with params
    app.route("/cards/:id")
    .get(api.findById)
    .delete(api.delete)
    .put(api.alter)
};