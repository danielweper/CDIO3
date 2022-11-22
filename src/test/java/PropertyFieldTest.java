import junit.framework.TestCase;

public class PropertyFieldTest extends TestCase {
    public void testNoOwnerWhenPropertyFieldIsCreated(){
        PropertyField propertyField = new PropertyField(2, Color.YELLOW);
        assertNull(propertyField.getOwner());
    }
    public void testThereIsAnOwnerWhenPropertyHasBeenBought(){
        PropertyField propertyField = new PropertyField(3, Color.GREEN);
        propertyField.setOwner(new Player("player1", 1000, 2));
        assertNotNull(propertyField.getOwner());
    }
    public void testIfPropertyIsOwnedByThePlayerAndThenLandOnActionIsNOTHING(){
        PropertyField propertyField = new PropertyField(4,Color.GREEN);
        Player player = new Player("PropertyOwner", 1000, 1);
        propertyField.setOwner(player);

        LandOnAction expected = LandOnAction.NOTHING;
        LandOnAction actual = propertyField.landedOn(player);
        assertEquals(expected, actual);
    }
    public  void testIfPropertyIsNotOwnedByAnyPlayerAndThenLandOnActionIsBuyProperty(){
        PropertyField propertyField = new PropertyField(4, Color.GREEN);
        Player player = new Player("PropertyOwner", 1000, 2);

        LandOnAction expected = LandOnAction.BUY_PROPERTY;
        LandOnAction actual = propertyField.landedOn(player);
        assertEquals(expected, actual);
    }
    public void testIfPropertyIsOwnedByAnotherPlayerAndThenLandOnActionIsPayRent(){
        PropertyField propertyField = new PropertyField(4, Color.GREEN);
        Player player = new Player("NotPropertyOwner", 1000, 1);
        Player player1 = new Player("PropertyOwner", 1000, 2);

        propertyField.setOwner(player);
        LandOnAction expected = LandOnAction.PAY_RENT;
        LandOnAction actual = propertyField.landedOn(player1);
        assertEquals(expected, actual);
    }

    // ingen ejer til at starte med
    // hvis spiller selv ejer, skal landonaction være nothing
    // hvis ingen ejer skal landonaction være buy
    // hvis anden spiller ejer skal landonaction være pay
}