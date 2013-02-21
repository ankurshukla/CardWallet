package com.ankur.cardwallet.datasource;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class CardDAO {
	
	private SQLiteDatabase database;
	private DBHelper dbHelper;
	
	private String[] allColumns = {dbHelper.COLUMN_ID,
			dbHelper.COLUMN_NAME, dbHelper.COLUMN_ALIAS,dbHelper.COLUMN_CARDNUM,dbHelper.COLUMN_CVV,dbHelper.COLUMN_EXPDATE, dbHelper.COLUMN_BILLINGZIP };
	
	
	public CardDAO(Context context){
		dbHelper = new DBHelper(context);
	}
	
	public void open() throws SQLException{
		database = dbHelper.getWritableDatabase();
	}
	
	public void close(){
		if(database != null){
			dbHelper.close();
		}
	}
	
	public void createCard(Card card){
		ContentValues values = new ContentValues();
		values.put(dbHelper.COLUMN_NAME, card.getCardHolderName());
		values.put(dbHelper.COLUMN_ALIAS, card.getAlias());
		values.put(dbHelper.COLUMN_CARDNUM, card.getCardNumber());
		values.put(dbHelper.COLUMN_CVV, card.getCvv());
		values.put(dbHelper.COLUMN_EXPDATE, card.getExpiryDate());
		values.put(dbHelper.COLUMN_BILLINGZIP, card.getBillingZip());
		
		database.insert(dbHelper.TABLE_CARD_INFO, null,values);
	}
	
	public List<Card> getAllCards() {
		List<Card> cards = new ArrayList<Card>();

		Cursor cursor = database.query(dbHelper.TABLE_CARD_INFO,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Card card = cursorToCard(cursor);
			cards.add(card);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return cards;
	}
	
	private Card cursorToCard(Cursor cursor) {
		Card card = new Card();
		card.setId(cursor.getLong(0));
		card.setCardHolderName(cursor.getString(1));
		card.setAlias(cursor.getString(2));
		card.setCardNumber(cursor.getString(3));
		card.setCvv(cursor.getString(4));
		card.setExpiryDate(cursor.getString(5));
		card.setBillingZip(cursor.getString(6));
		return card;
	}
	
	public boolean valueExists(String key, String value){
		boolean exist = false;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM "+ dbHelper.TABLE_CARD_INFO + " WHERE "+ key + " = '" +value +"'", null);
		if (c.getCount() > 0){
			exist = true;
		}
		c.close();
		
		return exist;
	}

}
