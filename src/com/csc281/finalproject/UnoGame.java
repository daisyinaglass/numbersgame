package com.csc281.finalproject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class UnoGame extends ActionBarActivity implements OnTouchListener {

	float x,y;
	static PlayingField playingfield;
	ArrayList<Card> deck = new ArrayList<Card>();
	ArrayList<Card> myHand = new ArrayList<Card>();
	ArrayList<Card> compHand = new ArrayList<Card>();
	Card activeCard;
	int endX, endY;
	float myTop, myBot, actTop, actBot;

	@SuppressLint("ClickableViewAccessibility")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// populating deck with each color
		//to randomly add to each deck, find a random index, make sure it's empty, and add
		
		x = y = 0;
		for (int j = 0; j < 10; j++) {
			// blue cards
			deck.add(new Card(j, Color.BLUE));
			deck.add(new Card(j, Color.BLUE));
		}
		for (int j = 0; j < 10; j++) {
			// yellow cards
			deck.add(new Card(j, Color.YELLOW));
			deck.add(new Card(j, Color.YELLOW));
		}
		for (int j = 0; j < 10; j++) {
			// green cards
			deck.add(new Card(j, Color.GREEN));
			deck.add(new Card(j, Color.GREEN));
		}
		for (int j = 0; j < 10; j++) {
			// red cards
			deck.add(new Card(j, Color.RED));
			deck.add(new Card(j, Color.RED));
		}
		
		//shuffling the deck
		long seed = System.nanoTime();
		Collections.shuffle(deck, new Random(seed));
		
		// know the color values
		//System.out.println("Green: " + Color.GREEN + ", Blue: " + Color.BLUE + ", Yellow: " + Color.YELLOW + ", Red: " + Color.RED);

		// populating player deck
		for (int c = 0; c < 7; c++) {
			myHand.add(deck.get(0));
			//System.out.println("Adding to player hand: " + deck.get(0).color + deck.get(0).number);
			deck.remove(0);

		}

		// populating computer deck
		for (int c = 0; c < 7; c++) {
			compHand.add(deck.get(0));
			//System.out.println("Adding to computer hand: " + deck.get(0).color + deck.get(0).number);
			deck.remove(0);
		}

		// next card is active card
		activeCard = deck.get(0);
		//System.out.println("Active card is " + deck.get(0).color + deck.get(0).number);
		deck.remove(0);

		playingfield = new PlayingField(this);
		setContentView(playingfield);

		playingfield.setOnTouchListener(this);
		System.out.println("setting the onTouch Listener");
		
		Toast toast = Toast.makeText(getApplicationContext(),"WELCOME TO NUMBERS!", Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
		toast.show();
		toast = Toast.makeText(getApplicationContext(),"TO PLAY...", Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
		toast.show();
		toast = Toast.makeText(getApplicationContext(),"MATCH YOUR CARD'S NUMBER OR COLOR", Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 300);
		toast.show();
		toast = Toast.makeText(getApplicationContext(),"TO THE ACTIVE CARD", Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, -150);
		toast.show();
		toast = Toast.makeText(getApplicationContext(),"IF NONE MATCH, DRAW A CARD", Toast.LENGTH_LONG);
		toast.setGravity(Gravity.LEFT|Gravity.TOP, 150, 100);
		toast.show();
		toast = Toast.makeText(getApplicationContext(),"AFTER YOU PLAY OR DRAW...", Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
		toast.show();
		toast = Toast.makeText(getApplicationContext(),"IT'S THE COMPUTER'S TURN", Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
		toast.show();
		toast = Toast.makeText(getApplicationContext(),"GET RID OF ALL YOUR CARDS TO WIN!", Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
		toast.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.uno_game, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public class PlayingField extends View {
		
		Bitmap table;
		public PlayingField(Context context) {
			super(context);
			table = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.table),1000,1000,false);
			
		}
		protected void onDraw(Canvas g) {

			Paint paint = new Paint();
			paint.setTextSize(50);
			
			g.drawBitmap(table, 0, 0, paint);
			// draw player hand at the bottom
			for (int i = 0; i < myHand.size(); i++) {
				paint.setColor(Color.WHITE);
				myTop = (float) (g.getHeight() * .6);
				myBot = (float) (g.getHeight() * .7);
				// the width is 50, the height is the difference between 60% and
				// 70% of the screen
				g.drawRect(105 * i, myTop, 105 * i + 100, myBot, paint);
				// draw numbers on hand of cards
				paint.setColor(myHand.get(i).color);
				g.drawText(Integer.toString(myHand.get(i).number), 105 * i + 38, (float) (g.getHeight() * .65), paint);
			}

			
			// draw active card
			paint.setColor(Color.WHITE);
			g.drawRect((float) (g.getWidth() * .5), (float) (g.getHeight() * .3), (float) (g.getWidth() * .5) + 100, (float) (g.getHeight() * .4), paint);
			// draw numbers on active card
			// setting the color, making the text bigger, and drawing at 50% the
			// window width and 35% the height
			paint.setColor(activeCard.color);
			g.drawText(Integer.toString(activeCard.number), (float) (g.getWidth() * .5) + 38, (float) (g.getHeight() * .35), paint);

			// draw button for drawing more cards
			paint.setColor(Color.CYAN);
			g.drawRect(0, 0, 200, 100, paint);
			paint.setColor(Color.BLACK);
			g.drawText("DRAW", 30, 70, paint);
			
		}

	}

	// @SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent e) {

		switch (e.getAction()) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_UP:
			//System.out.println("The window was touched: " + e.getX() + ", " + e.getY());

			endX = (int) (e.getX() / 100);
			// if hit the draw button
			if (e.getX() <= 200 && e.getY() <= 100) {
				if (deck.size()==0)
				{
					//if the deck is empty, defer to computer turn
					System.out.println("Deck is empty");
					break;
				}
				else 
				{
					myHand.add(0, deck.get(0));
					//System.out.println("Adding to player hand: " + deck.get(0).color + deck.get(0).number);
					deck.remove(0);
					playingfield.postInvalidate();
				}
				
				
				//switch to computer turn
				
				//iterate through computer deck
				for (int i = 0; i<compHand.size(); i++)
				{
					//System.out.println("Computer's turn");
					//compare each card to active card
					//if find match, play & end turn
					if (compHand.get(i).color == activeCard.color || compHand.get(i).number == activeCard.number)
					{
						Toast toast = Toast.makeText(getApplicationContext(),"COMPUTER PLAYED. YOUR TURN!", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, -300);
						toast.show();
						// if is valid move, move & switch turn
						//System.out.println("Computer played");
						//add the old active card back to the unplayed deck
						deck.add(activeCard);
						//make the active card the card that was selected from the hand
						activeCard = compHand.get(i);
						//remove the selected card from the hand
						compHand.remove(i);
						//redraw
						playingfield.postInvalidate();
						break;
					}else
					{
						//if not, draw card & end turn
						if (deck.size()==0)
						{
							System.out.println("Deck is empty");
							break;
						}
						else
						{
							Toast toast = Toast.makeText(getApplicationContext(),"COMPUTER DREW CARD. YOUR TURN!", Toast.LENGTH_SHORT);
							toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, -300);
							toast.show();
							compHand.add(0, deck.get(0));
							//System.out.println("Adding to comp hand: " + deck.get(0).color +" "+ deck.get(0).number);
							deck.remove(0);
							playingfield.postInvalidate();
							break;
						}
					}
				
				}
			}
			//else if trying to play a card
			//		if the touch is within the y bounds 	&&	the touch is within the x bounds AND my color == active card color 			OR	my number				==	active card #
			else if ((e.getY() >= myTop && e.getY() <= myBot) && e.getX()<(myHand.size()*100) && (myHand.get(endX).color == activeCard.color || myHand.get(endX).number == activeCard.number)) {
				// if hit one of the cards, check to see if valid move
				// if is valid move, move & switch turn
				//System.out.println("Correct match");
				//add the old active card back to the unplayed deck
				deck.add(activeCard);
				//make the active card the card that was selected from the hand
				activeCard = myHand.get(endX);
				//remove the selected card from the hand
				myHand.remove(endX);
				//redraw
				playingfield.postInvalidate();
				
				//if our deck is now empty (winning condition)
				if (myHand.size()==0)
				{
					Toast toast = Toast.makeText(getApplicationContext(),"YOU WIN!!", Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
					toast.show();
					toast = Toast.makeText(getApplicationContext(),"TO PLAY AGAIN, DRAW 7 MORE CARDS", Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
					toast.show();
					//System.out.println("YOU WIN!!");
					break;
				}
				
				
				//and switch to computer turn
				
				//iterate through computer deck
				for (int i = 0; i<compHand.size(); i++)
				{
					//System.out.println("Computer's turn");
					//compare each card to active card
					//if find match, play & end turn
					if (compHand.get(i).color == activeCard.color || compHand.get(i).number == activeCard.number)
					{
						Toast toast = Toast.makeText(getApplicationContext(),"COMPUTER PLAYED. YOUR TURN!", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, -300);
						toast.show();
						// if is valid move, move & switch turn
						//System.out.println("Computer played");
						//add the old active card back to the unplayed deck
						deck.add(activeCard);
						//make the active card the card that was selected from the hand
						activeCard = compHand.get(i);
						//remove the selected card from the hand
						compHand.remove(i);
						//redraw
						playingfield.postInvalidate();
						break;
					}else
					{
						//if not, draw card & end turn
						if (deck.size()==0)
						{
							System.out.println("Computer tried to access deck; deck is empty");
							break;
						}
						else
						{
							Toast toast = Toast.makeText(getApplicationContext(),"COMPUTER DREW CARD. YOUR TURN!", Toast.LENGTH_SHORT);
							toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, -300);
							toast.show();
							compHand.add(0, deck.get(0));
							//System.out.println("Adding to comp hand: " + deck.get(0).color +" "+ deck.get(0).number);
							deck.remove(0);
							playingfield.postInvalidate();
							break;
						}
					}
				
				}
			} 
			
			
		}
		return true;
	}
}
