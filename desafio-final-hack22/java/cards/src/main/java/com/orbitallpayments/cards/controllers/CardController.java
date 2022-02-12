package com.orbitallpayments.cards.controllers;

import com.orbitallpayments.cards.models.Card;
import com.orbitallpayments.cards.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    // GET - list of all registers
    @GetMapping
    public ResponseEntity<List<Card>> findAllCard(){
        List<Card> cardList = cardService.findAll();

        return new ResponseEntity(cardList, HttpStatus.OK);
    }

    // POST - save a register
    @PostMapping
    public ResponseEntity<Card> saveCard(@RequestBody Card card){
        Card cardResponse = cardService.save(card);

        return new ResponseEntity(cardResponse, HttpStatus.CREATED);
    }

    // GET - find register by id
    @GetMapping("/{id}")
    public ResponseEntity<Card> findByIdCard(@PathVariable("id") Long id){
        Optional<Card> card = cardService.findById(id);

        if(!card.isPresent()){
            return new ResponseEntity("Registro não encontrado!", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(card, HttpStatus.OK);
    }

    // PUT - alter register by id
    @PutMapping("/{id}")
    public ResponseEntity<Object> alterCard(@RequestBody Card changedCard, @PathVariable("id") Long id){
        Optional<Card> card = cardService.findById(id);

        if(!card.isPresent()){
            return new ResponseEntity("Registro não encontrado!", HttpStatus.NOT_FOUND);
        }

        changedCard.setId(card.get().getId());
        cardService.save(changedCard);

        return new ResponseEntity(changedCard, HttpStatus.ACCEPTED);
    }

    // DELETE - delete register by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCard(@PathVariable("id") Long id){
        Optional<Card> card = cardService.findById(id);

        if(!card.isPresent()){
            return new ResponseEntity("Registro não encontrado!", HttpStatus.NOT_FOUND);
        }

        cardService.delete(card.get());

        return new ResponseEntity("Registro deletado com sucesso!", HttpStatus.OK);
    }

    // GET - list of all registers ordered by customer name
    @GetMapping("/paginationAndSorting")
    public ResponseEntity<Page<Card>> findAllPageCard(@PageableDefault(page = 0, size = 5, sort = "customerName", direction = Sort.Direction.ASC) Pageable pageable){

        Page<Card> cardList = cardService.findAllPage(pageable);

        return new ResponseEntity(cardList, HttpStatus.OK);
    }
}
