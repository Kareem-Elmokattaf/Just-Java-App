/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int price_depends = 5;
    int coffee_counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        int price=0;
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = false , hasChocolate = false;
        EditText nameofcustomer = (EditText) findViewById(R.id.nameofcustomer);
        String name = nameofcustomer.getText().toString();

        if(whippedCreamCheckBox.isChecked())
        {
            hasWhippedCream = true;
        }

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolateCheckBox);

        if(chocolateCheckBox.isChecked())
        {
            hasChocolate = true;
        }

        price = calculatePrice(coffee_counter,hasWhippedCream,hasChocolate);
        price_depends = 5;
        String priceMessage = creatOrderSummary(price, hasWhippedCream, hasChocolate, name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if(intent.resolveActivity(getPackageManager())!= null) {
            startActivity(intent);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This increments the quantity value
     */
    public void incrementQuantity(View view){
        ++coffee_counter;
        displayQuantity(coffee_counter);
    }

    public void decrementQuantity(View view) {
        --coffee_counter;
        if(coffee_counter <= 0)
        {
            coffee_counter=0;
            Toast.makeText(this,"You can't have less than 0 coffees", Toast.LENGTH_SHORT).show();
        }
        displayQuantity(coffee_counter);
    }

    private int calculatePrice(int quan,boolean whipped_cream, boolean chocolate_in) {
        if (whipped_cream) {
            ++price_depends;
        }
        if (chocolate_in)
        {
            price_depends += 2;
        }
        return quan * price_depends;
    }

    private String creatOrderSummary(int price, boolean whippedcream, boolean chocolate, String name)
    {
        String priceMessage = "Name: " + name;
        priceMessage += "\nQuantity: "+ coffee_counter;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\nAdd whipped cream? " + whippedcream;
        priceMessage += "\nAdd chocolate? " + chocolate;
        priceMessage += "\nThank you!";
        return priceMessage;
    }
}