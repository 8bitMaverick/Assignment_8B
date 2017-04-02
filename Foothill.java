/* Courtney Hunt JAVA 1B Assignment 8 Part 2 Foothill College
 * 
 * This program has a stack order of objects that holds the ranking of
 * the card game chosen, default and the big two are used as examples.
 * The ranks for value ordering and suits are set and two lists are created
 * with a random card chosen and added to each list, pushing and popping
 * between the default and the big two ranks as they are added. The resulting
 * lists are displayed with the cards sorted by the intended rank for each list
 * myList 1 is the default, myList 2 is the big two version.
 */

import java.util.*;

public class Foothill
{
   public static void main(String[] args)
   {
      // create Iterator
      ListIterator<Card> iter;

      // temporary Card
      Card tempCard;

      // ranking order for game "Tchoo Da Dee" ("the big two"):
      char[] valueRanks =
         { '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A', '2' };
      Card.Suit[] suitRanks =
         { Card.Suit.diamonds, Card.Suit.clubs, Card.Suit.hearts,
               Card.Suit.spades };

      // default order array
      char[] defVal = new char[Card.numValsInOrderingArray];
      Card.Suit[] defSuit;

      // clone, don't copy the reference
      defVal = Card.valueRanks.clone();
      defSuit = Card.suitRanks.clone();

      // instantiate two lists
      LinkedList<Card> myList1, myList2;

      myList1 = new LinkedList<Card>();
      myList2 = new LinkedList<Card>();

      // pushOrdering() the default to the stack
      CardWOrderStk.pushOrdering(defVal, defSuit);

      // inserting the cards into the lists
      for (int i = 0; i < 20; i++)
      {
         tempCard = generateRandomCard();

         // insert() the card to the first list, default ranks
         insert(myList1, tempCard);

         // pushOrdering() new rank to the card class
         CardWOrderStk.pushOrdering(valueRanks, suitRanks);

         // insert() the card to the second list, new ranks
         insert(myList2, tempCard);

         // popOrdering() to default ranks
         CardWOrderStk.popOrdering();
      }

      // show myList1
      createLine(80, "-");
      System.out.println("Show List 1: (default ranks)");
      createLine(80, "-");
      for (iter = myList1.listIterator(); iter.hasNext();)
         System.out.println(iter.next().toString());

      // show myList2
      createLine(80, "-");
      System.out.println("Show List 2: (Tchoo Da Dee ranks)");
      createLine(80, "-");
      for (iter = myList2.listIterator(); iter.hasNext();)
         System.out.println(iter.next().toString());
      createLine(80, "-");

   }

   // Global method definitions --------------------------
   static void insert(LinkedList<Card> myList, Card x)
   {
      ListIterator<Card> iter;
      Card tempCard;

      for (iter = myList.listIterator(); iter.hasNext(); )
      {
         tempCard = iter.next();
         if (x.compareTo(tempCard) <= 0)
         {
            iter.previous(); // back up one
            break;
         }
      }
      iter.add(x);
   }

   static boolean remove(LinkedList<Card> myList, Card x)
   {
      ListIterator<Card> iter;

      for (iter = myList.listIterator(); iter.hasNext(); )
      {
         if (iter.next().compareTo(x) == 0)
         {
            iter.remove();
            return true;   // we found, we removed, we return
         }
      }
      return false;
   }

   static boolean removeAll(LinkedList<Card> myList, Card x)
   {
      ListIterator<Card> iter;
      boolean bool = false;

      for (iter = myList.listIterator(); iter.hasNext();)
         if (iter.next().compareTo(x) == 0)
         {
            iter.remove();
            bool = true;
         }

      return bool;
   }

   static Card generateRandomCard()
   {
      Card.Suit suit;
      char val;

      int suitSelector, valSelector;

      // get random suit and value
      suitSelector = (int) (Math.random() * 4);
      valSelector = (int) (Math.random() * 13);

      // pick suit
      suit = Card.Suit.values()[suitSelector];

      // pick value
      valSelector++;   // put in range 1-14
      switch(valSelector)
      {
      case 1:
         val = 'A';
         break;
      case 10:
         val = 'T';
         break;
      case 11:
         val = 'J';
         break;
      case 12:
         val = 'Q';
         break;
      case 13:
         val = 'K';
         break;
      case 14:
         val = 'X';
         break;
      default:
         val = (char)('0' + valSelector);   // simple way to turn n into 'n'   
      }

      return new Card(val, suit);
   }

   // helps beautify the console output
   private static void createLine(int length, String shape)
   {
      for (int counter = 0; counter < length; counter += 1)
      {
         System.out.print(shape);
      }
   }
}

//Card class -------------------------------------------------------------------
class Card implements Comparable<Card>
{   
   // type and constants
   public enum Suit { clubs, diamonds, hearts, spades }

   // for ordering
   public static char[] valueRanks = { '2', '3', '4', '5', '6', '7', '8', '9', 
         'T', 'J', 'Q', 'K', 'A'};
   static Suit[] suitRanks = {Suit.clubs, Suit.diamonds, Suit.hearts, 
         Suit.spades};
   static int numValsInOrderingArray = 13;

   // private data
   private char value;
   private Suit suit;
   boolean errorFlag;

   // 4 overloaded constructors
   public Card(char value, Suit suit)
   {
      set(value, suit);
   }

   public Card(char value)
   {
      this(value, Suit.spades);
   }
   public Card()
   {
      this('A', Suit.spades);
   }
   // copy constructor
   public Card(Card card)
   {
      this(card.value, card.suit);
   }

   // mutators
   public boolean set(char value, Suit suit)
   {
      char upVal;            // for upcasing char

      // convert to uppercase to simplify
      upVal = Character.toUpperCase(value);

      if ( !isValid(upVal, suit))
      {
         errorFlag = true;
         return false;
      }

      // else implied
      errorFlag = false;
      this.value = upVal;
      this.suit = suit;
      return true;
   }

   // accessors
   public char getVal()
   {
      return value;
   }

   public Suit getSuit()
   {
      return suit;
   }

   public boolean getErrorFlag()
   {
      return errorFlag;
   }

   public boolean equals(Card card)
   {
      if (this.value != card.value)
         return false;
      if (this.suit != card.suit)
         return false;
      if (this.errorFlag != card.errorFlag)
         return false;
      return true;
   }

   // stringizer
   public String toString()
   {
      String retVal;

      if (errorFlag)
         return "** illegal **";

      // else implied

      retVal =  String.valueOf(value);
      retVal += " of ";
      retVal += String.valueOf(suit);

      return retVal;
   }

   // helper
   private boolean isValid(char value, Suit suit)
   {
      char upVal;

      // convert to uppercase to simplify (need #include <cctype>)
      upVal = Character.toUpperCase(value);

      // check for validity
      if (
            upVal == 'A' || upVal == 'K'
            || upVal == 'Q' || upVal == 'J'
            || upVal == 'T' 
            || (upVal >= '2' && upVal <= '9')
            )
         return true;
      else
         return false;
   }

   // sort member methods
   public int compareTo(Card otherCard)
   {
      if (this.value == otherCard.value)
         return ( getSuitRank(this.suit) - getSuitRank(otherCard.suit) );

      return ( 
            getValueRank(this.value) 
            - getValueRank(otherCard.value) 
            );
   }

   //helpers for compareTo()
   public static void setRankingOrder(char[] valueOrder, Suit[] suitOrder,
         int numValsInOrderingArray)
   {
      int k;

      if (numValsInOrderingArray < 0 || numValsInOrderingArray > 13)
         return;

      Card.numValsInOrderingArray = numValsInOrderingArray;

      for (k = 0; k < numValsInOrderingArray; k++)
         Card.valueRanks[k] = valueOrder[k];

      for (k = 0; k < 4; k++)
         Card.suitRanks[k] = suitOrder[k];
   }

   public static int getSuitRank(Suit st)
   {
      int k;

      for (k = 0; k < 4; k++) 
         if (suitRanks[k] == st)
            return k;

      // should not happen
      return 0;
   }

   public  static int getValueRank(char val)
   {
      int k;

      for (k = 0; k < numValsInOrderingArray; k++) 
         if (valueRanks[k] == val)
            return k;

      // should not happen
      return 0;
   }
}

class CardWOrderStk extends Card
{
   private static Stack<OrderObject> rankList = new Stack<OrderObject>();

   static private class OrderObject
   {
      private char[] valueRanks;
      private Card.Suit[] suitRanks;

      OrderObject(char[] valueRanks, Card.Suit[] suitRanks)
      {
         this.valueRanks = valueRanks;
         this.suitRanks = suitRanks;
      }
   }

   public static void pushOrdering(char[] valueRanks, Card.Suit[] suitRanks)
   {
      rankList.push(new OrderObject(valueRanks, suitRanks));

      Card.setRankingOrder(valueRanks, suitRanks, Card.numValsInOrderingArray);
   }

   public static void popOrdering()
   {
      rankList.pop();

      Card.setRankingOrder(rankList.peek().valueRanks,
            rankList.peek().suitRanks, Card.numValsInOrderingArray);
   }
}

/* Sample Run-------------------------------------------------------------------
--------------------------------------------------------------------------------
Show List 1: (default ranks)
--------------------------------------------------------------------------------
2 of hearts
2 of hearts
2 of spades
5 of hearts
5 of spades
6 of hearts
8 of clubs
8 of diamonds
8 of hearts
8 of spades
8 of spades
9 of diamonds
T of hearts
J of spades
Q of hearts
Q of hearts
K of diamonds
K of spades
A of spades
A of spades
--------------------------------------------------------------------------------
Show List 2: (Tchoo Da Dee ranks)
--------------------------------------------------------------------------------
5 of hearts
5 of spades
6 of hearts
8 of diamonds
8 of clubs
8 of hearts
8 of spades
8 of spades
9 of diamonds
T of hearts
J of spades
Q of hearts
Q of hearts
K of diamonds
K of spades
A of spades
A of spades
2 of hearts
2 of hearts
2 of spades
--------------------------------------------------------------------------------
------------------------------------------------------------------------------*/