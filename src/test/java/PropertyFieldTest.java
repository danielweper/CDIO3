import junit.framework.TestCase;

public class PropertyFieldTest extends TestCase {
    // -----------------------------------------------------------------------------------
    // denne test metode tester for om et felt eksisterer
    // asserNull: hvis en property eksistere gennemføres testen succefuldt
    // -----------------------------------------------------------------------------------
    public void testNoOwnerWhenPropertyFieldIsCreated(){
        PropertyField propertyField = new PropertyField(2, PropertyColor.YELLOW);

        assertNull(propertyField.getOwner());
    }
    // -----------------------------------------------------------------------------------
    // denne test metode tester for, hvis spiller selv ejer, skal landonaction være nothing
    // *
    // assertNotNull: hvis der er en ejer gennemføres testen succefuldt
    // -----------------------------------------------------------------------------------
    public void testThereIsAnOwnerWhenPropertyHasBeenBought(){
        PropertyField propertyField = new PropertyField(3, PropertyColor.GREEN);

        // *her sætter jeg en owner til at være Player og den player bliver lavet på samme linjie
        propertyField.setOwner(new Player("player1", 1000, 2));

        assertNotNull(propertyField.getOwner());
    }

    // -----------------------------------------------------------------------------------
    // denne test metode tester for, hvis spiller selv ejer, skal landonaction være nothing
    // *
    // **
    // assertEquals: Assert at to objekter er er lige(equal)
    // hvis spiller lander på eget felt og handlingen er Nothing gennemføres testen
    // -----------------------------------------------------------------------------------
    public void testIfPropertyIsOwnedByThePlayerAndThenLandOnActionIsNOTHING(){
        PropertyField propertyField = new PropertyField(4, PropertyColor.GREEN);
        Player player = new Player("PropertyOwner", 1000, 1);
        propertyField.setOwner(player);

        // * forventet
        LandOnAction expected = LandOnAction.NOTHING;
        // * spilleren landede på sit eget felt
        LandOnAction actual = propertyField.landedOn(player);
        assertEquals(expected, actual);
    }

    // -----------------------------------------------------------------------------------
    // denne metode tester for, hvis ingen ejer skal landonaction være buy
    // her bliver der ikke sat en ejer expected er derfor Buy
    // assertEquals: når player lander på et ikke eget felt gennemføres testen succefuldt
    // -----------------------------------------------------------------------------------
    public  void testIfPropertyIsNotOwnedByAnyPlayerAndThenLandOnActionIsBuyProperty(){
        PropertyField propertyField = new PropertyField(4, PropertyColor.GREEN);
        Player player = new Player("PropertyOwner", 1000, 2);

        LandOnAction expected = LandOnAction.BUY_PROPERTY;
        LandOnAction actual = propertyField.landedOn(player);
        assertEquals(expected, actual);
    }

    // -----------------------------------------------------------------------------------
    // denne metode tester for hvis anden spiller ejer skal landonaction være pay
    // assertEquals: når player lander på player1 skal landOnAction være Pay,
    // så gennemføres testen succefuldt
    // -----------------------------------------------------------------------------------
    public void testIfPropertyIsOwnedByAnotherPlayerAndThenLandOnActionIsPayRent(){
        PropertyField propertyField = new PropertyField(4, PropertyColor.GREEN);
        Player player = new Player("NotPropertyOwner", 1000, 1);
        Player player1 = new Player("PropertyOwner", 1000, 2);

        propertyField.setOwner(player);
        LandOnAction expected = LandOnAction.PAY_RENT;
        LandOnAction actual = propertyField.landedOn(player1);
        assertEquals(expected, actual);
    }

}