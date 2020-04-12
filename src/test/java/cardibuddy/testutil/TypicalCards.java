package cardibuddy.testutil;

import static cardibuddy.logic.commands.CommandTestUtil.VALID_TAG_FRONTEND;
import static cardibuddy.logic.commands.CommandTestUtil.VALID_TAG_HARD;
import static cardibuddy.logic.commands.CommandTestUtil.VALID_TITLE_DJANGO;
import static cardibuddy.logic.commands.CommandTestUtil.VALID_TITLE_REACT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cardibuddy.model.CardiBuddy;
import cardibuddy.model.deck.Deck;
import cardibuddy.model.flashcard.Card;

/**
 * A utility class containing a list of {@code Deck} objects to be used in tests.
 */
public class TypicalCards {
    public static final Card q1 = new CardBuilder().withQuestion("This is question 1").
            withTFAnswer("true").buildFlashcard();


    public static final Deck POSTGRESQL = new DeckBuilder().withTitle("PostgreSQL")
            .withTags("Database", "Relational").build();
    public static final Deck CARL = new DeckBuilder().withTitle("Carl Kurz").build();
    public static final Deck DANIEL = new DeckBuilder().withTitle("Daniel Meier").withTags("friends").build();
    public static final Deck ELLE = new DeckBuilder().withTitle("Elle Meyer").build();
    public static final Deck FIONA = new DeckBuilder().withTitle("Fiona Kunz").build();
    public static final Deck GEORGE = new DeckBuilder().withTitle("George Best").build();

    // Manually added
    public static final Deck HOON = new DeckBuilder().withTitle("Hoon Meier").build();
    public static final Deck IDA = new DeckBuilder().withTitle("Ida Mueller").build();

    // Manually added - Deck's details found in {@code CommandTestUtil}
    public static final Deck DJANGO = new DeckBuilder().withTitle(VALID_TITLE_DJANGO)
            .withTags(VALID_TAG_FRONTEND).build();
    public static final Deck REACT = new DeckBuilder().withTitle(VALID_TITLE_REACT)
            .withTags(VALID_TAG_HARD, VALID_TAG_FRONTEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalDecks() {} // prevents instantiation

    /**
     * Returns an {@code CardiBuddy} with all the typical decks.
     */
    public static CardiBuddy getTypicalCardiBuddy() {
        CardiBuddy cb = new CardiBuddy();
        for (Deck deck : getTypicalDecks()) {
            cb.addDeck(deck);
        }
        return cb;
    }

    public static List<Deck> getTypicalDecks() {
        return new ArrayList<>(Arrays.asList(ASYNCHRONOUS, POSTGRESQL, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}

