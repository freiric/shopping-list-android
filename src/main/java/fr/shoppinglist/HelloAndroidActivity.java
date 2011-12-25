package fr.shoppinglist;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.validation.ConstraintDeclarationException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.caucho.hessian.client.HessianProxy;
import com.caucho.hessian.client.HessianProxyFactory;
import com.shoppinglist.R;
import com.shoppinglist.model.IItemDao;
import com.shoppinglist.model.Item;

public class HelloAndroidActivity extends Activity {

	private static String TAG = "shopping-list-android";

	/**
	 * Called when the activity is first created.
	 * 
	 * @param savedInstanceState
	 *            If the activity is being re-initialized after previously being
	 *            shut down then this Bundle contains the data it most recently
	 *            supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it
	 *            is null.</b>
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		setContentView(R.layout.main);
		final TextView helloWorld = (TextView) findViewById(R.id.textView1);
		final Button sayHelloButton = (Button) findViewById(R.id.button1);
		sayHelloButton.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				sayHelloButton.setEnabled(false);
				helloWorld.setText(R.string.contacting_server);

				new AsyncTask<Void, Void, String>() {

					@Override
					protected String doInBackground(Void... arg0) {
						String doInBackgroundResult = "";
						final HessianProxyFactory factory = new HessianProxyFactory();
						//
						// // try {
						IItemDao itemDao;
						// try {
						// url = new URL("http://10.0.2.2:8080/ItemDao");
						// } catch (MalformedURLException e) {
						// e.printStackTrace();
						// throw new RuntimeException(e);
						// }
						// HessianProxy proxy = new MyHessianProxy(factory,
						// url);
						// itemDao = (IItemDao) Proxy.newProxyInstance(cl,
						// new Class[] { IItemDao.class, IItemDao.class },
						// proxy);

						try {
							itemDao = (IItemDao) factory.create(IItemDao.class,
									"http://10.0.2.2:8080/ItemDao");
							List<Item> findAllItem = itemDao.findAllItem();
							for (Item author : findAllItem) {
								System.out.println(author.getName());
								doInBackgroundResult += author.getName();

							}
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
						return doInBackgroundResult;
					}

					@Override
					protected void onPostExecute(String result) {
						helloWorld.setText(result);
						sayHelloButton.setEnabled(true);
					}
				}.execute();
			}

		});

	}

}
