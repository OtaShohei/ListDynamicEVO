package jp.egaonohon.activity.list.dynamic.evo;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	/**
	 * Activityを継承したクラスにおいては、メンバ変数の領域でこれらを指定するのが定石。
	 */
	EditText et1=null;
	Button	bt1=null;
	ArrayAdapter<String> adapter=null;
	ArrayList<String> data=null;

	/**
	 * エディットテキストとボタンの参照を引っ張ってくることをonCreateでやっている。
	 */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    et1=(EditText)findViewById(R.id.et1);
    bt1=(Button)findViewById(R.id.btn1);


    /**
     * アレイリストの宣言はメンバ変数側で行っているのでここではしない。
     */
    data = new ArrayList<String>();
//    data.add("胡椒");
//    data.add("ターメリック");
//    data.add("コリアンダー");
//    data.add("生姜");
//    data.add("ニンニク");
//    data.add("サフラン");
    data.add("レッドチリ");
    data.add("ハバネロ");

    /*
     * String[] data = new String[]{ "胡椒", "ターメリック", "コリアンダー", "生姜", "ニンニク",
     * "サフラン" };
     */

    /**
     * アダプタの宣言もメンバ変数側で行っているのでここではしない。
     */
    adapter = new ArrayAdapter<String>(
    		//第一引数は？＝＝＞コンテキスト
    		this,
    		//第二引数は？＝＝＞一行のフォーマット。新しく作ったrow.xmlを示している。
    		R.layout.row,
    		//第三引数は？＝＝＞表示するテキストのリスト
    		data);
    //レイアウトファイルのリストビューから参照を取得
    ListView list = (ListView) findViewById(R.id.list);
    //setAdapter()メソッドでアダプタ経由でデータと一行のフォーマットを指定
    list.setAdapter(adapter);

    /**
     * ボタンのオンクリックリスナー。エディットテキストから持ってきた文字列を追加する。
     */
    bt1.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
			// 元データに追加
			data.add(et1.getText().toString());
			//アダプタに元データを変更したよ、と知らせる。
			adapter.notifyDataSetChanged();
		}
	});


	/**
	 * ここからが改造箇所!!
	 * setOnItemClickListenerを実装するとある一行をクリックした時のアクションを実装できるようになる。
	 * クリック可能なリストを作る感じ。
	 *
	 * ちなみに、長押しの時に処理を走らせるのは…
	 * setOnItemLongClickListener
	 * …となる。
	 */
	list.setOnItemClickListener(new AdapterView.OnItemClickListener() {// OnItemClickListenerが実装すべきインターフェース。
		/**
		 * implementsするメソッドがonItemClick()。
		 *
		 * 第1引数は、リストビューの参照（AdapterView<?> av）。<?>となっているがリストビューの参照。
		 * 第2引数は、クリックされた行の参照。今回は実質的にはテキストビュー。
		 * 第3引数は、アダプタの何番目の行に相当するか。それがposition。
		 * 第4引数は、idとは、今は使っていないが、行ごとのid番号。
		 */

		public void onItemClick(AdapterView<?> av, View view, int position,
				long id) {
			/**
			 * 選択された行を削除している。 ここの記述を、別のActivityに飛ばすなど記述すればいろいろできるようになる!!
			 */
			adapter.remove((String) ((TextView) view).getText());
			/*
			 * getApplicationContext()でこのメソッドが存在している大本のcontextを取得できる。
			 * thisでうまくいかない時はこれ。
			 *
			 * ここでは、各行のidがどうなっているかをトーストで表示させてみる。
			 */
			Toast.makeText(getApplicationContext(),
					"pos=" + position + "id=" + id + "を削除しました", Toast.LENGTH_SHORT)
					.show();

		}
	});

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_main, menu);
    return true;
  }
}
