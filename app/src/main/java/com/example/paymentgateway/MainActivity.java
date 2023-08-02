package com.example.paymentgateway;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {

    EditText amount;
    TextView paymentid;
    int cash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Checkout.preload(getApplicationContext());

        setContentView(R.layout.activity_main);
         paymentid = findViewById(R.id.paymentid);
        Button payButton = findViewById(R.id.paybutton);
        amount = findViewById(R.id.amount);



        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!amount.getText().toString().isEmpty())
                {


                    cash = Integer.parseInt(amount.getText().toString());
                    startPayment(cash);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Please Enter Amount", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void startPayment(int amounts) {


        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_85eEePBHsKUJk6");

        checkout.setImage(R.drawable.img);

        final Activity activity = this;


        try {
            JSONObject options = new JSONObject();

            options.put("name", "guddu lawaniya");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
           // options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", amounts*100);//pass amount in currency subunits
            options.put("prefill.email", "guddulawaniya123@gmail.com");
            options.put("prefill.contact","7037282643");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);


        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        paymentid.setText("successful payment id :"+s);

    }

    @Override
    public void onPaymentError(int i, String s) {

        paymentid.setText("Failed Payment  :"+s);

    }
}