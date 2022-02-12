package com.orbitallpayments.cards.services;

import com.orbitallpayments.cards.models.Card;
import com.orbitallpayments.cards.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    // Method to save a register in the database and alter a register in the database
    public Card save(Card card){
        return cardRepository.save(card);
    }

    // Method to get a list of all register in the database
    public List<Card> findAll(){
        List<Card> cardList = new ArrayList();
        cardRepository.findAll().forEach(cardList :: add);
        return cardList;
    }

    // Method to find a register in the database by id
    public Optional<Card> findById(Long id){
        return cardRepository.findById(id);
    }

    // Method to delete a register by id in the database
    public void delete(Card card){
        cardRepository.delete(card);
    }

    // Method to get a list of all register in the database ordered
    public Page<Card> findAllPage(Pageable pageable) {
        return cardRepository.findAll(pageable);
    }
}
