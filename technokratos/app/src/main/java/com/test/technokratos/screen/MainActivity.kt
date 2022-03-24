package com.test.technokratos.screen

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.test.technokratos.QuestApp
import com.test.technokratos.R
import com.test.technokratos.databinding.ActivityMainBinding
import com.test.technokratos.request.questList.QuestListViewModel
import com.test.technokratos.utils.NetworkUtils

lateinit var binding: ActivityMainBinding
lateinit var questListViewModel: QuestListViewModel

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        questListViewModel = ViewModelProvider(this)[QuestListViewModel::class.java]

        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPref", MODE_PRIVATE)
        val saveCount: Int = sharedPreferences.getInt("count", 0)

        if (saveCount > 0) {
            loadData()
        }

        if (NetworkUtils.isInternetAvailable(applicationContext)) {
            questListViewModel.fetchResult((application as QuestApp).questApi)
        } else {
            Toast.makeText(
                applicationContext,
                "Ошибка, отсутвует интернет-соединение",
                Toast.LENGTH_LONG
            )
                .show()
        }

        var phone = questListViewModel.phone
        var coordinates =
            questListViewModel.coordinatesLatitude + " ; " + questListViewModel.coordinatesLongitude

        binding.button.setOnClickListener {
            if (NetworkUtils.isInternetAvailable(applicationContext)) {
                questListViewModel.fetchResult((application as QuestApp).questApi)
                var count = 0
                count++
                val sharedPreferences = getSharedPreferences("sharedPref", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.clear().apply {
                    putString("picture", questListViewModel.pictureLarge)
                    putString(
                        "name",
                        "Полное имя: " + questListViewModel.name + " " + questListViewModel.nameLast
                    )
                    putString("birthday", "Дата Рождения: " + questListViewModel.birthday)
                    putString("phone", "Номер телефона: " + questListViewModel.phone)
                    putString(
                        "country",
                        "Страна проживания: " + questListViewModel.country + " Город: " + questListViewModel.city + " Улица: " + questListViewModel.streetName + " , " + questListViewModel.streetNumber
                    )
                    putString(
                        "coordinates",
                        "Координаты: { " + questListViewModel.coordinatesLatitude + " ; " + questListViewModel.coordinatesLongitude + " }"
                    )
                    putInt("count", count)
                }.apply()

                binding.button.text = "Обновить данные"
                Glide.with(this).load(questListViewModel.pictureLarge)
                    .placeholder(R.drawable.loading_image).into(binding.pictureImage)
                binding.textName.text =
                    "Полное имя: " + questListViewModel.name + " " + questListViewModel.nameLast
                binding.textBirthday.text = "Дата Рождения: " + questListViewModel.birthday
                phone = questListViewModel.phone
                binding.textPhoneNumber.text = "Номер телефона: " + questListViewModel.phone
                binding.textCountry.text =
                    "Страна проживания: " + questListViewModel.country + " Город: " + questListViewModel.city + " Улица: " + questListViewModel.streetName + " , " + questListViewModel.streetNumber
                binding.textCoordinates.text =
                    "Координаты: { " + questListViewModel.coordinatesLatitude + " ; " + questListViewModel.coordinatesLongitude + " }"
                coordinates =
                    questListViewModel.coordinatesLatitude + ";" + questListViewModel.coordinatesLongitude

            } else {
                Toast.makeText(
                    applicationContext,
                    "Ошибка, отсутвует интернет-соединение",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }
        binding.textPhoneNumber.setOnClickListener {
            callToNumber(phone)
        }

        binding.textCoordinates.setOnClickListener {
            searchGpsNavigation(coordinates)
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun searchGpsNavigation(coordinates: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:${coordinates})"))
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun callToNumber(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${phone}"))
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n", "CheckResult")
    private fun loadData() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPref", MODE_PRIVATE)
        val savedPicture: String? = sharedPreferences.getString("picture", "")
        val savedName: String? = sharedPreferences.getString("name", "")
        val savedBirthday: String? = sharedPreferences.getString("birthday", "null")
        val savedPhone: String? = sharedPreferences.getString("phone", "null")
        val savedCountry: String? = sharedPreferences.getString("country", "null")
        val savedCoordinates: String? = sharedPreferences.getString("coordinates", "null")

        Glide.with(this).load(savedPicture).placeholder(R.drawable.loading_image)
            .into(binding.pictureImage)
        binding.textName.text = "$savedName"
        binding.textBirthday.text = "$savedBirthday"
        binding.textPhoneNumber.text = "$savedPhone"
        binding.textCountry.text = "$savedCountry"
        binding.textCoordinates.text = "$savedCoordinates"
    }
}