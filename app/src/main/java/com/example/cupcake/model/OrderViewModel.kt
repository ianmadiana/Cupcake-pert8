package com.example.cupcake.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

//menentukan harga per cupcake dideklarisikan sebagai konstanta (nilai tidak berubah)
private const val PRICE_PER_CUPCAKE = 2.00
//menentukan harga per cupcake jika diambil di hari yang sama, variabel ditentukan sebagai konstanta (nilai tidak berubah)
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

//class ini digunakan untuk menyimpan data aplikasi
class OrderViewModel : ViewModel() {

    //inisialisasi menggunakan metode getPickupOptions()
    val dateOptions = getPickupOptions()

    //gunakan "_" pada nama variabel untuk properti private
    //data untuk quantity
    private val _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> = _quantity

    //data untuk flavor
    private val _flavor = MutableLiveData<String>()
    val flavor: LiveData<String> = _flavor

    //data untuk date
    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    //data untuk price
    private val _price = MutableLiveData<Double>()
    //Transformations.map()
    val price: LiveData<String> = Transformations.map(_price) {
        NumberFormat.getCurrencyInstance().format(it)
    }

    //method untuk quantity
    fun setQuantity(numberCupcakes: Int) {
        _quantity.value = numberCupcakes

        //perbarui variabel harga saat jumlahnya ditetapkan
        updatePrice()
    }

    //method untuk flavor
    fun setFlavor(desiredFlavor: String) {
        _flavor.value = desiredFlavor
    }

    //method untuk pickup date
    fun setDate(pickupDate: String) {
        _date.value = pickupDate

        //memanggil method helper updatePrice()
        updatePrice()
    }

    //method untuk apakah flavor telah ditetapkan atau belum
    fun hasNoFlavorSet(): Boolean {
        return _flavor.value.isNullOrEmpty()
    }

    //fungsi untuk menampilkan daftar tanggal
    private fun getPickupOptions(): List<String> {
        val options = mutableListOf<String>()

        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())

        val calendar = Calendar.getInstance()

        //membuat daftar tanggal dimulai dengan tanggal saat ini dan tiga tanggal berikutnya
        repeat(4) {
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }

        return options
    }

    //fungsi untuk mereset properti atau item cupcake
    fun resetOrder() {
        _quantity.value = 0
        _flavor.value = ""
        _date.value = dateOptions[0]
        _price.value = 0.0
    }

    //digunakan untuk melakukan inisialisasi properti saat instance OrderViewModel dibuat
    init {
        resetOrder()
    }

    //fungsi untuk menghitung harga
    private fun updatePrice() {
        //quantity.value adalah jumlah cupcake yang dipesan
        var calculatedPrice = (quantity.value ?: 0) * PRICE_PER_CUPCAKE

        //apakah pembeli mengambil dihari yang sama? jika iya maka ada biaya tambahan
        if (dateOptions[0] == _date.value) {
            calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
        }
        _price.value = calculatedPrice
    }

}