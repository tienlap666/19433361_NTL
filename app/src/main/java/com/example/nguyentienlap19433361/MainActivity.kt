package com.example.nguyentienlap19433361

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import java.math.RoundingMode
import android.widget.Button;

class MainActivity : AppCompatActivity() {
    private val MAX_PRICE: Double = 20 * 9999.0
    private val magicNumber: Int = 6
    private lateinit var prices: Array<TextView>
    private lateinit var newPrice: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val count20d = findViewById<TextView>(R.id.count20d)
        val count10d = findViewById<TextView>(R.id.count10d)
        val count05d = findViewById<TextView>(R.id.count05d)
        val count01d = findViewById<TextView>(R.id.count01d)
        val count25c = findViewById<TextView>(R.id.count25c)
        val count10c = findViewById<TextView>(R.id.count10c)
        val count5c = findViewById<TextView>(R.id.count5c)
        val count1c = findViewById<TextView>(R.id.count1c)

        val button = findViewById<Button>(R.id.button)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)
        val button4 = findViewById<Button>(R.id.button4)
        val button5 = findViewById<Button>(R.id.button5)
        val button6 = findViewById<Button>(R.id.button6)
        val button7 = findViewById<Button>(R.id.button7)
        val button8 = findViewById<Button>(R.id.button8)
        val button9 = findViewById<Button>(R.id.button9)
        val button10 = findViewById<Button>(R.id.button10)
        val button11 = findViewById<Button>(R.id.button11)


        prices = arrayOf(
            count20d, count10d,
            count05d, count01d,
            count25c, count10c,
            count5c, count1c
        )

        val currAmount = findViewById<TextView>(R.id.currAmount)
        newPrice = if (
            currAmount.text.toString() == "0.00"
        ) "" else currAmount.text.toString()

        /* function that handles when a button is pressed */
        fun onButtonClicked(btn: TextView) {
            /* default value = -1 is clear button */
            var value: Int = -1

            /* change value when button is not clear button */
            if (btn != button11) {
                value = btn.text.toString().toInt()
            }

            /* clear button is pressed */
            if (value == -1) {
                newPrice = ""
                currAmount.text = "0.00"
                prices.forEach { it -> it.text = "0" }
                return
            }

            /* change current price */
            newPrice += value.toString()
            newPrice = newPrice.toInt().toString()

            var parsedNumber = newPrice.toDouble() / 100.0

            /* current amount is too big */
            if (newPrice != "") {
                if (parsedNumber > MAX_PRICE) {
                    Toast.makeText(this, getString(R.string.alertBigNum), Toast.LENGTH_SHORT).show()
                    return
                }
            }

            currAmount.text = parsedNumber.toString()

            val listPrices = arrayOf(20.0, 10.0, 5.0, 1.0, 0.25, 0.1, 0.05, 0.01)
            for (i in listPrices.indices) {
                var amounts = (parsedNumber / listPrices[i]).toInt()
                prices[i].text = amounts.toString()
                parsedNumber -= amounts.toDouble() * listPrices[i]
                parsedNumber = parsedNumber.toBigDecimal().setScale(magicNumber, RoundingMode.UP).toDouble()
            }
        }

        /* array of button */


        arrayOf<TextView>(
            button, button2, button3,
            button4, button5, button6,
            button7, button8, button9,
            button10, button11
        ).forEach { it -> it.setOnClickListener() {
            onButtonClicked(it as TextView)
        }}
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val currAmount = findViewById<TextView>(R.id.currAmount)
        var currPrice: TextView = currAmount
        var oldPrices: ArrayList<String> = ArrayList()

        for (i in prices) {
            oldPrices.add(i.text.toString())
        }

        outState.putString("currPrice", currPrice.text.toString())
        outState.putString("priceprice", newPrice)
        outState.putStringArrayList("oldPrices", oldPrices)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val currAmount = findViewById<TextView>(R.id.currAmount)
        currAmount.text = savedInstanceState.getString("currPrice")
        newPrice = savedInstanceState.getString("priceprice")!!
        var oldPrices = savedInstanceState.getStringArrayList("oldPrices")

        for (i in prices.indices) {
            prices[i].text = oldPrices?.get(i) ?: "0"
        }
    }

}

