package com.example.interviewprep.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.interviewprep.models.ComicsData
import com.example.interviewprep.models.Result
import com.example.interviewprep.network.DataRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DataViewModel(private val repository: DataRepository):ViewModel() {
val dataList=MutableLiveData<List<Result>>()
    val errorMessage=MutableLiveData<String>()
    lateinit var disposable:Disposable

    fun getAllCharacters() {
        val response = repository.getAllCharacters()

        response.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { getDataListObserver()}
    }

    private fun getDataListObserver(): Observer<ComicsData> {
        return object : Observer<ComicsData> {
            override fun onComplete() {
                //hide progress indicator .

            }

            override fun onError(e: Throwable) {
                errorMessage.postValue(e.message)
                println(e)
                dataList.postValue(mutableListOf())
            }

            override fun onNext(t: ComicsData) {
                println(t)
                dataList.postValue(t.data.results)

            }

            override fun onSubscribe(d: Disposable) {
                disposable = d
                //start showing progress indicator.
            }
        }
    }
}