package com.test.technokratos.request.questList

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.test.technokratos.request.QuestApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class QuestListViewModel(application: Application) : AndroidViewModel(application) {
    private val compositeDisposable = CompositeDisposable()

    var status: Boolean = false
    var name: String = "Запросите заново"
    var pictureLarge: String = "Запросите заново"
    var phone: String = "Запросите заново"
    var nameLast: String = "Запросите заново"
    var birthday: String = "Запросите заново"
    var country: String = "Запросите заново"
    var city: String = "Запросите заново"
    var streetName: String = "Запросите заново"
    var streetNumber: String = "Запросите заново"
    var coordinatesLatitude: String = "Запросите заново"
    var coordinatesLongitude: String = "Запросите заново"


    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()

    }

    fun fetchResult(questApi: QuestApi) {
        questApi.let {
            compositeDisposable.add(
                questApi.getQuestList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe ({ posts ->
                        displayItem(posts)
                        status = true
                        name = posts?.results?.first()?.name?.first.toString()
                        phone = posts?.results?.first()?.phone.toString()
                        nameLast = posts?.results?.first()?.name?.last.toString()
                        pictureLarge = posts?.results?.first()?.picture?.large.toString()
                        birthday = posts?.results?.first()?.dob?.date?.dropLast(14).toString()
                        country = posts?.results?.first()?.location?.country.toString()
                        city = posts?.results?.first()?.location?.city.toString()
                        streetName = posts?.results?.first()?.location?.street?.name.toString()
                        streetNumber = posts?.results?.first()?.location?.street?.number.toString()
                        coordinatesLatitude =
                            posts?.results?.first()?.location?.coordinates?.latitude.toString()
                        coordinatesLongitude =
                            posts?.results?.first()?.location?.coordinates?.longitude.toString()
                    }, {
                        Log.e("ERROR","error")
                    })
            )
        }
    }

    private fun displayItem(posts: QuestListResponse?) {
        Log.e("Image:", posts?.results?.first()?.picture?.large.toString())
        Log.e("Name first", posts?.results?.first()?.name?.first.toString())
        Log.e("Name last", posts?.results?.first()?.name?.last.toString())
        Log.e("Birthday", posts?.results?.first()?.dob?.date?.dropLast(14).toString())
    }
}
