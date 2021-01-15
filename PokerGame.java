/*
 * Class that plays the poker game
 */
import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

/**
 *
 * @author Cameron and Jared
 */
public class PokerGame {
    Deck cards;
    Card[] hand1 = new Card[5];
    Card[] hand2 = new Card[5];
    
    //creates the deck and has it shuffle and then deals 5 cards to each hand
    public PokerGame() {
      cards = new Deck();
      cards.shuffle();
      
      for (int i=0; i<5; i++) {
         hand1[i] = cards.deal();
         hand2[i] = cards.deal();
      }
    }
    
    
    
    //Plays rounds until all the cards have been dealt
    public void playGame() {
      while(!cards.drawPileIsEmpty()) {
         if (!cards.drawPileIsEmpty()) {
            System.out.println("Player 1's turn:");
            playTurn(hand1, 1);
         } else
            break;
         
         if (!cards.drawPileIsEmpty()) {
            System.out.println("Player 2's turn:");
            playTurn(hand2, 2);
         } else
            break;
      }
    }
    
    //Runs through one player turn
    private void playTurn(Card[] hand, int player) {
      JFrame handFrame = displayHand(hand);
      handFrame.setTitle("Player " + Integer.toString(player) + "'s hand");
      JFrame discardFrame = null;
      if (!cards.discardPileIsEmpty()) {
         discardFrame = displayCard(cards.topOfDiscard());
         discardFrame.setTitle("Discard Pile");
      }
      Scanner scanner = new Scanner(System.in);
      
      System.out.println("Do you want to:");
      System.out.println("1. Draw from the draw pile");
      if (!cards.discardPileIsEmpty()) 
         System.out.println("2. Pick up from the discard pile");
      int choice = scanner.nextInt();
      
      if (choice == 1) {
         Card c = cards.deal();
         JFrame drawFrame = displayCard(c);
         drawFrame.setTitle("Drawn Card");
         addToHand(c, hand);
         drawFrame.dispose();
      } else if (choice == 2) {
         addToHand(cards.pickup(), hand);
      }
      
      handFrame.dispose();
      if (discardFrame != null)
         discardFrame.dispose();
    }
    
    //Adds a card to a hand at the specified position
      private void addToHand(Card c, Card[] hand) {
      Scanner scanner = new Scanner(System.in);
      System.out.println("Do you want to keep this card?");
      System.out.println("1. Yes");
      System.out.println("2. No");
      int choice = scanner.nextInt();
      
      if (choice == 1) {
         System.out.println("Which card to you want to discard? (Leftmost = 1, rightmost = 5)");
         choice = scanner.nextInt();
         cards.discard(hand[choice - 1]);
         hand[choice - 1] = c;
      } else if (choice == 2) {
         cards.discard(c);
      }
    }
    
    //Checks hand for a flush returns an integer value to compare with either player
    public boolean isFlush(Card[] hand){
      if(hand[0].getSuit().equals(hand[1].getSuit()) && hand[1].getSuit().equals(hand[2].getSuit()) && hand[3].getSuit().equals(hand[4].getSuit()))
         return true;
      else{
         return false;
      }
    }
    
    //checks the hand for a straight
    public boolean isStraight(Card[] hand){
      boolean flag = true;
      for (int i = 1; i < 5; i++) {
         if (hand[i].getValue() != hand[0].getValue() + i) {
            flag = false;
            break;
         }
      }
      return flag;
    }
    
    public boolean isThreeOfAKind(Card[] hand) {
      boolean flag = false;
      for (int i = 0; i < 3; i++)
         if (hand[i].getValue() == hand[i + 1].getValue() && hand[i].getValue() == hand[i + 2].getValue())
            flag = true;
      return flag;
    }
    
    public boolean isFourOfAKind(Card[] hand) {
      boolean flag = false;
      for (int i = 0; i < 2; i++)
         if (hand[i].getValue() == hand[i + 1].getValue() && hand[i].getValue() == hand[i + 2].getValue() && hand[i].getValue() == hand[i + 3].getValue())
            flag = true;
      return flag;
    }
    
    //checks the hand of a player for a one pair
    public boolean isOnePair(Card[] hand) {
      boolean flag = false;
      for (int i = 0; i < 4; i++)
         if (hand[i].getValue() == hand[i+1].getValue())
            flag = true;
      return flag;
    }
    
    
    public boolean isTwoPair(Card[] hand){
      isOnePair(hand);
      if(isOnePair(hand)){
         for(int i = 4; i > 1; i--){
            if(hand[i].getValue() == hand[i - 1].getValue())
               return true;
         }
      }
      return false;
    }
    
    public boolean isFullHouse(Card[] hand){
      return isThreeOfAKind(hand) && isOnePair(hand);
    }  
      
    public boolean isRoyalFlush(Card[] hand){
     if(hand[0].getSuit().equals(hand[1].getSuit()) && hand[1].getSuit().equals(hand[2].getSuit()) && hand[3].getSuit().equals(hand[4].getSuit())){
         if(isFlush(hand))
            return true;
     }
     return false;    
    }
    
    public boolean isStraightFlush(Card[] hand) {
      return isStraight(hand) && isFlush(hand);
    }
    
    public int bestHand(Card[] hand, int player){
      if(isRoyalFlush(hand))
         return 9;
      else if(isStraightFlush(hand))
         return 8;
      else if(isFourOfAKind(hand))
         return 7;
      else if(isFullHouse(hand))
         return 6;
      else if(isFlush(hand))
         return 5;
      else if(isStraight(hand))
         return 4;
      else if(isThreeOfAKind(hand))
         return 3;
      else if(isTwoPair(hand))
         return 2;
      else if(isOnePair(hand))
         return 1;
      else
         return 0;
    
    }
    
    
    
    //looks at the hands and determines the winner.  Returns a 1 or a 2 depending
    //on which hand was the best
    public int determineWinner() {
         if(bestHand(hand1, 1) > bestHand(hand2, 2))
            return 1;
         else if(bestHand(hand2, 2) > bestHand(hand1, 1))
            return 2;
        else{
        return 0;
    }
    }
    public JFrame displayHand(Card[] hand) {
      JFrame cardFrame = new JFrame();
      cardFrame.setLayout(new FlowLayout());
      JPanel cardPanel = new JPanel();
      cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.X_AXIS));
      for (Card c : hand) {
        String fn = c.getFileName();
        String fullname = "cards/" + fn;
        ImageIcon cardIcon = new ImageIcon(new ImageIcon(fullname).getImage().getScaledInstance(100, 125, Image.SCALE_DEFAULT));
        JLabel cardLabel = new JLabel(cardIcon);
        cardPanel.add(cardLabel);
      }
      
      cardFrame.add(cardPanel);
      cardFrame.setSize(550,175);
      cardFrame.setLocation(100,100);
      cardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      cardFrame.setVisible(true);
  
      return cardFrame;
    }

      
  public JFrame displayCard(Card c) {
    JFrame cardFrame = new JFrame();
    cardFrame.setLayout(new FlowLayout());
    String fullname = "cards/" + c.getFileName();
    ImageIcon cardIcon = new ImageIcon(new ImageIcon(fullname).getImage().getScaledInstance(100, 125, Image.SCALE_DEFAULT));
    JLabel cardLabel = new JLabel(cardIcon);
    cardFrame.add(cardLabel);
    cardFrame.setSize(110,175);
    cardFrame.setLocation(100,300);
    cardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    cardFrame.setVisible(true);
    
    return cardFrame;
  }
   

  
 }
