package dk.unf.software.aar2013.gruppe5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SprintActivity extends Activity {

	private TextView score;
	private TextView qu;
	private Button ba1;// Button answer 1
	private Button ba2;
	private Button ba3;
	private Button ba4;
	QuizMechanics qm = new QuizMechanics();
	List<Integer> answerList;
	List<Integer> questionList;
	ArrayList<String> answers;
	int i = 0;
	ArrayList<Integer> highScores;
	SharedPreferences prefs;
	public TextView count;
	boolean cancel;
	CountDownTimer countDownTimer;
	

	public void reset() {
		i = 0;
	}
	
//	public void counter(){
//		count = (TextView) findViewById(R.id.counter);
//		count.setText("test");
//		new CountDownTimer(15000, 1000) {
//			
//		     public void onTick(long millisUntilFinished) {
//		         count.setText(millisUntilFinished / 1000 + "");
//		      
//		     }
//
//		     public void onFinish() {
//		         count.setText("done!");
//		     }
//		     
//		  }.start();
//		  
//	}
	
	public void highScore() {
		if (qm.score > highScores.get(9)) {
			Log.d("yolol", "4202");
			highScores.add(qm.score);
			Collections.sort(highScores);
			Collections.reverse(highScores);
			Editor edit = prefs.edit();
			for (int i = 0; i != 10; i++) {
				Log.d("no!", highScores.get(i) + "");
				edit.putInt("highscore" + i, highScores.get(i));
			}
			edit.commit();
			
		}
		Intent intent = new Intent(getApplicationContext(),
				HighscoreActivity2.class);
		startActivity(intent);
	}

	public boolean checkAnswer(String a, String ca) {
		if (a == ca) {
			Toast.makeText(getApplicationContext(), "RIGTIGT!",
					Toast.LENGTH_SHORT).show();

			qm.score++;
			score = (TextView) findViewById(R.id.score);
			score.setText("Dine points: " + qm.score);
			Log.d("ASASGAS", qm.allQuestions.size() + "");
			if (i+1 >= qm.allQuestions.size()) {
				Toast.makeText(getApplicationContext(), "Ikke flere spørgsmål tilbage.",
						Toast.LENGTH_LONG).show();
				ArrayList<Integer> temp = new ArrayList<Integer>();
				highScore();
				return false;
			}
			nextQuestion();
			return true;
		} else {
			Toast.makeText(getApplicationContext(), "FORKERT!",
					Toast.LENGTH_SHORT).show();
			qm.score--;
			score = (TextView) findViewById(R.id.score);
			score.setText("Dine points: " + qm.score);
			if (i+1 >= qm.allQuestions.size()) {
				Toast.makeText(getApplicationContext(), "Ikke flere spørgsmål tilbage.",
						Toast.LENGTH_LONG).show();
				ArrayList<Integer> temp = new ArrayList<Integer>();
				highScore();
				return false;
			}
			nextQuestion();
			return false;
		}
	}

	public List<Integer> randomList(int x) {
		List<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i != x; i++) {
			result.add(i);
		}
		Collections.shuffle(result);
		return result;
	}

	public void nextQuestion() {
		answerList = randomList(4);
		Log.d("as", questionList + " " + i);
		answers = qm.allQuestions.get(questionList.get(i)).getAnswers(); // Answer1
		Log.d("heyo", answers + "");
		// a2 = qm.allQuestions.get(i).answers.get(list.get(1));
		// a3 = qm.allQuestions.get(i).answers.get(list.get(2));
		// a4 = qm.allQuestions.get(i).answers.get(list.get(3));

		ba1 = (Button) findViewById(R.id.at1);
		ba1.setText(answers.get(answerList.get(0)));
		ba2 = (Button) findViewById(R.id.at2);
		ba2.setText(answers.get(answerList.get(1)));
		ba3 = (Button) findViewById(R.id.at3);
		ba3.setText(answers.get(answerList.get(2)));
		ba4 = (Button) findViewById(R.id.at4);
		ba4.setText(answers.get(answerList.get(3)));
		qu = (TextView) findViewById(R.id.question);
		qu.setText(qm.allQuestions.get(questionList.get(i)).question + "");
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sprint);
		count = (TextView) findViewById(R.id.counter);
//		counter();
		Intent intent = new Intent(getApplicationContext(),
				HighscoreActivity2.class);
		countDownTimer = new MyCountdownTimer(60000, 1000, count, this);
		countDownTimer.start();
		
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		Editor edit = prefs.edit();
		// edit.putInt("highscore1", i);
		// edit.clear();
		edit.commit();
		highScores = new ArrayList<Integer>();
		for (int i = 0; i != 10; i++) {
			highScores.add(prefs.getInt("highscore" + i, 0));
		}
		
		questionList = randomList(qm.allQuestions.size());

		nextQuestion();
		Log.d("test", answers.get(0) + " " + answerList.get(0));
		
		ba1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (checkAnswer(answers.get(answerList.get(0)),
						qm.allQuestions.get(questionList.get(i)).answers.get(0))) {
					
				}
				i++;
			}
		});

		ba2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (checkAnswer(answers.get(answerList.get(1)),
						qm.allQuestions.get(questionList.get(i)).answers.get(0))) {
				}
				i++;
			}
		});

		ba3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (checkAnswer(answers.get(answerList.get(2)),
						qm.allQuestions.get(questionList.get(i)).answers.get(0))) {
				}
				i++;
			}
		});

		ba4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (checkAnswer(answers.get(answerList.get(3)),
						qm.allQuestions.get(questionList.get(i)).answers.get(0))) {
				}
				i++;
			}
		});
		
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
