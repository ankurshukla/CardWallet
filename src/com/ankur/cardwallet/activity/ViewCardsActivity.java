package com.ankur.cardwallet.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.ankur.cardwallet.R;
import com.ankur.cardwallet.datasource.Card;
import com.ankur.cardwallet.datasource.CardDAO;

public class ViewCardsActivity extends ListActivity {

	private CardDAO cardDAO = new CardDAO(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_cards);
		
		GetCardsTask task = new GetCardsTask(this);
		task.execute();

	}
	
	private class GetCardsTask extends AsyncTask<Void, Void, List<Card>> {
		
		private ViewCardsActivity callingActivity;
		
		public GetCardsTask(ViewCardsActivity context){
			callingActivity = context;
		}

		@Override
		protected List<Card> doInBackground(Void ...args) {
			
			cardDAO.open();
			List<Card> cards = cardDAO.getAllCards();
			
			List<Card> values = new ArrayList<Card>();
			
			Iterator<Card> iterCard = cards.iterator();
			while(iterCard.hasNext()){
				Card tempCard = iterCard.next();values.add(tempCard);
			}
			return values;
		}
		
		@Override
		protected void onPostExecute(List<Card> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			 ArrayAdapter<Card> adapter = new ArrayAdapter<Card>(callingActivity,
						android.R.layout.simple_list_item_1, result);
		        callingActivity.setListAdapter(adapter);
		}
		
	}

}
