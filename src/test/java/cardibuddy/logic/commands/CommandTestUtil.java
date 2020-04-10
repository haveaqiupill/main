package cardibuddy.logic.commands;

import static cardibuddy.logic.parser.CliSyntax.PREFIX_DECK;
import static cardibuddy.logic.parser.CliSyntax.PREFIX_TAG;
import static cardibuddy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cardibuddy.commons.core.index.Index;
import cardibuddy.logic.CommandHistory;
import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.CardiBuddy;
import cardibuddy.model.Model;
import cardibuddy.model.deck.Deck;
import cardibuddy.model.deck.SearchDeckKeywordsPredicate;
import cardibuddy.testutil.EditDeckDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_TITLE_DJANGO = "Django";
    public static final String VALID_TITLE_REACT = "React";
    public static final String VALID_TAG_HARD = "Hard";
    public static final String VALID_TAG_FRONTEND = "Frontend";

    public static final String TITLE_DESC_DJANGO = " " + PREFIX_DECK + VALID_TITLE_DJANGO;
    public static final String TITLE_DESC_REACT = " " + PREFIX_DECK + VALID_TITLE_REACT;
    public static final String TAG_DESC_FRONTEND = " " + PREFIX_TAG + VALID_TAG_FRONTEND;
    public static final String TAG_DESC_HARD = " " + PREFIX_TAG + VALID_TAG_HARD;

    public static final String INVALID_TITLE_DESC = " " + PREFIX_DECK + "PHP&"; // '&' not allowed in names
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "Scripting*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditDeckDescriptor DESC_DJANGO;
    public static final EditCommand.EditDeckDescriptor DESC_REACT;

    static {
        DESC_DJANGO = new EditDeckDescriptorBuilder().withName(VALID_TITLE_DJANGO)
                .withTags(VALID_TAG_FRONTEND).build();
        DESC_REACT = new EditDeckDescriptorBuilder().withName(VALID_TITLE_REACT)
                .withTags(VALID_TAG_HARD, VALID_TAG_FRONTEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel, CommandHistory commandHistory) {
        try {
            CommandResult result = command.execute(actualModel, commandHistory);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel, CommandHistory commandHistory) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel, commandHistory);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered deck list and selected deck in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        CardiBuddy expectedCardiBuddy = new CardiBuddy(actualModel.getCardiBuddy());
        List<Deck> expectedFilteredList = new ArrayList<>(actualModel.getFilteredDeckList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedCardiBuddy, actualModel.getCardiBuddy());
        assertEquals(expectedFilteredList, actualModel.getFilteredDeckList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the deck at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showDeckAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredDeckList().size());

        Deck deck = model.getFilteredDeckList().get(targetIndex.getZeroBased());
        final String[] splitName = deck.getTitle().toString().split("\\s+");
        model.updateFilteredDeckList(new SearchDeckKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredDeckList().size());
    }

}
