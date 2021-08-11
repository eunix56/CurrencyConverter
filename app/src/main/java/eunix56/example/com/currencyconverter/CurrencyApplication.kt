package eunix56.example.com.currencyconverter

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import eunix56.example.com.currencyconverter.data.db.CurrencyDatabase
import eunix56.example.com.currencyconverter.data.network.*
import eunix56.example.com.currencyconverter.data.provider.CurrencyItemProvider
import eunix56.example.com.currencyconverter.data.provider.CurrencyItemProviderImpl
import eunix56.example.com.currencyconverter.data.repository.CurrencyConverterRepository
import eunix56.example.com.currencyconverter.data.repository.CurrencyConverterRepositoryImpl
import eunix56.example.com.currencyconverter.ui.CurrencyViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class CurrencyApplication: Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@CurrencyApplication))

        bind() from singleton { CurrencyDatabase(instance()) }
        bind() from singleton { instance<CurrencyDatabase>().currencyRatesDao() }
        bind() from singleton { instance<CurrencyDatabase>().historyRatesDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ExchangeRateApiService(instance()) }
        bind<CurrencyNetworkDataSource>() with singleton { CurrencyNetworkDataSourceImpl(instance())}
        bind<CurrencyConverterRepository>() with singleton { CurrencyConverterRepositoryImpl(instance(), instance(), instance()) }
        bind() from provider { CurrencyViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}