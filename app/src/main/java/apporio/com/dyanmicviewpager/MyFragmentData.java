package apporio.com.dyanmicviewpager;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.ArrayList;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import apporio.com.dyanmicviewpager.singleton.VolleySingleton;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragmentData extends Fragment {

    public static ArrayList<String> product_id = new ArrayList<>();
    public static ArrayList<String> category_id = new ArrayList<>();
    public static ArrayList<String> category_name = new ArrayList<>();
    public static ArrayList<String> base_name = new ArrayList<>();
    public static ArrayList<String> base_price = new ArrayList<>();
    public static ArrayList<String> specification = new ArrayList<>();
    public static ArrayList<String> image = new ArrayList<>();

    public String catname;
    public String catid;
    private ListView listView;
    Context context;
    TextView total,unit;

    // newInstance constructor for creating fragment with arguments
    public static MyFragmentData newInstance(Context context,String catid) {


        MyFragmentData fragmentFirst = new MyFragmentData();
        Bundle args = new Bundle();

        //args.putString("someInt",catid);
       args.putString("someTitle", catid);

        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    //Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment_data, container, false);


        catid = getArguments().getString("someTitle");
        listView= (ListView) view.findViewById(R.id.listView);
        total= (TextView) view.findViewById(R.id.toal);
        unit= (TextView) view.findViewById(R.id.unit);



//method to parse data
         parseData(catid);

        Log.e("cat id",""+catid);







        return view;
    }



    public void parseData(final String catid) {

      String  url = "http://demo.apporio.com/laundry-app-development/api/view_product.php?merchant_id=0&category_id="+catid;



       Log.e("url      ",""+url);



            StringRequest productequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response)
                {


                    //Log.e("response",response);
                    product_id.clear();
                    category_id.clear();
                    image.clear();
                    specification.clear();
                    base_name.clear();
                    base_price.clear();
                    category_name.clear();

                 try {




                    GsonBuilder gsonBuilder = new GsonBuilder();
                    final Gson gson = gsonBuilder.create();




                    Product prorsponse = gson.fromJson(response, Product.class);

                    for (int i = 0; i < prorsponse.getResponse().getMessage().size(); i++) {


                        product_id.add(prorsponse.getResponse().getMessage().get(i).getProduct_id());
                        category_id.add(prorsponse.getResponse().getMessage().get(i).getCategory_id());
                        category_name.add(prorsponse.getResponse().getMessage().get(i).getCategory_name());
                        base_name.add(prorsponse.getResponse().getMessage().get(i).getBase_name());
                        base_price.add(prorsponse.getResponse().getMessage().get(i).getBase_price());
                        specification.add(prorsponse.getResponse().getMessage().get(i).getSpecification());
                        image.add(prorsponse.getResponse().getMessage().get(i).getImage());

                    }


                    CustomeAdapter custome = new CustomeAdapter(getActivity(), category_id, image, category_name, specification, base_price,total,unit);

                    listView.setAdapter(custome);

                    //Log.e("pro", "" + product_id + category_id + category_name + base_name + base_price);
                }
                 catch (Exception e){

                 }
                }



            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            RequestQueue requestQueue = VolleySingleton.getInstance(getActivity()).getRequestQueue();
            requestQueue.add(productequest);
        }

    }





