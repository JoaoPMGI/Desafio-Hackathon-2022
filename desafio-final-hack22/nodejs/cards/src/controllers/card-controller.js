const nedb = require('../configurations/database');
const api = {};

//POST - save a register in the database
api.save = (req, res) => {
    const canonical = req.body;
    
    nedb.insert(canonical, (exception, card) => {
        if(exception){
            const sentence = `Não foi possível salvar o registro no banco de dados.`;
            console.error(`Erro: ${sentence} - \n${exception}`);

            res.status(exception.status | 400);
            res.json({ "message": sentence });
        };

        console.log("Salvo com sucesso!");

        res.status(201);
        res.json(card);
    });
};

//GET - find all registers in the database
api.findAll = (req, res) => {
    nedb.find({  }).exec( (exception, cardList) => { 
        if(exception){
            const sentence = `Não foi possível retornar a lista de todos os registros no banco de dados.`;
            console.error(`Erro: ${sentence} - \n${exception}`);

            res.status(exception.status | 404);
            res.json({ "message": sentence });
        };

        console.log("Card list: \n" + cardList);

        //res.status(200) -> not necessary, 200 is default
        res.json(cardList);
    });
};

//GET - find a register in the database by id
api.findById = (req, res) => {
    nedb.findOne({ _id: req.params.id }).exec( (exception, card) => {
        if(exception){
            const sentence = `Não foi possível retornar o registro do banco de dados.`;
            console.error(`Erro: ${sentence} - \n${exception}`);

            res.status(exception.status | 404);
            res.json({ "message": sentence });
        };

        console.log(`Card: ${card}`);

        if(card === null){
            res.status(404)
            res.json({ message: "Registro não encontrado!" });

        } else {
            //res.status(200) -> not necessary, 200 is default
            res.json(card);
        }
    });
};

//PUT - alter a register in the databse
api.alter = (req, res) => {
    const canonical = req.body;

    nedb.update({ _id: req.params.id }, canonical, (exception) => {

        if(exception) {
            const sentence = `Não foi possível alterar o registro no banco de dados.`;
            console.error(`Erro: ${sentence} - \n${exception}`);

            res.status(exception.status | 400);
            res.json({ "message": sentence });
        }

        nedb.findOne({ _id: req.params.id }).exec( (exception, card) => {
            if(card === null){
                res.status(404)
                res.json({ message: "Registro não encontrado!" });
    
            } else {
                canonical._id = req.params.id;
                res.status(202)
                res.json(canonical)
            }
        });
    });
};

//DELETE - removing a register from database
api.delete = (req, res) => {
    nedb.remove({_id: req.params.id}, {}, (exception, numRemoved = 1) => {

        if(exception){
            const sentence = `Não foi possível deletar esse registro do banco de dados.`;
            console.log(`Erro: ${sentence} - \n${exception}`);

            res.status(exception.status | 400);
            res.json({ "message": sentence });
        }

        res.json({ message: "Registro deletado com sucesso!" });
    });
};

//GET - get all the registers from database ordered by customer name
api.findAllPage = (req, res) => {
    nedb.find({ }).sort({ customerName:1 }).skip(0).limit(3).exec( (exception, cardList) => { 
        if(exception){
            const sentence = `Não foi possível retornar a lista de todos os registros no banco de dados.`;
            console.error(`Erro: ${sentence} - \n${exception}`);

            res.status(exception.status | 404);
            res.json({ "message": sentence });
        };

        console.log("Card list: \n" + cardList);

        //res.status(200) -> not necessary, 200 is default
        res.json(cardList);
    });
}

module.exports = api;