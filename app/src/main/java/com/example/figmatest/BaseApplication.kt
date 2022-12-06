package com.example.figmatest

import android.app.Application
import com.example.figmatest.data.ApiServices
import com.example.figmatest.data.RetrofitInstance
import com.example.figmatest.domein.GetListOfItemModelUseCase
import com.example.figmatest.domein.Repository
import com.example.figmatest.ui.ItemViewModel
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModul)
        }
    }
        val appModul = module {
        single<Retrofit> {
            Retrofit.Builder()
                .baseUrl("https://dev.bgrem.deelvin.com/api/backgrounds")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        single<Repository> {
//            Repository(apiServices = get<Retrofit>().create(ApiServices::class.java))
            Repository()
        }
        factory<GetListOfItemModelUseCase> {
            GetListOfItemModelUseCase(repository = get())
        }
        viewModel<ItemViewModel> {
            ItemViewModel(
                getListOfItemModelUseCase = get()
            )
        }
    }
//    val repository: Repository by lazy { Repository() }
//    val getListOfItemModelUseCase: GetListOfItemModelUseCase by lazy {
//        GetListOfItemModelUseCase(
//            repository
//        )
//    }
}