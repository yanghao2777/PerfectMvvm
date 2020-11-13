# PerfactMvvm



## Download：

#### Gradle:
`
repositories {
    jcenter()
}

dependencies {
    implementation 'com.perfactmvvm.haohaodev:HaoPerfactMvvm:1.0.0'
}


`

## ProGuard

**Project used UtilCodeX，Retrofit2，Okio，Okhttp，Gson，And add extra rule:**
`
#--------------------------------------retrofit------------------------------------------#
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn javax.annotation.**
-dontwarn kotlin.Unit
-dontwarn retrofit2.KotlinExtensions
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>
-keep class retrofit2.** { *; }
-dontwarn retrofit2.**
-keepattributes Exceptions
-dontwarn javax.annotation.**
#-------------------------------------- end retrofit------------------------------------------#

#-------------------------------------- okhttp okio------------------------------------------#
-dontwarn org.conscrypt.**
-dontwarn java.lang.instrument.**
-dontwarn sun.misc.SignalHandler
-dontwarn org.codehaus.mojo.animal_sniffer.*
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.**{*;}
-dontwarn com.squareup.**
-dontwarn okio.**
-keep class com.squareup.okio.**{*;}
-keep public class org.codehaus.* { *; }
-keep public class java.nio.* { *; }
#-------------------------------------- end okhttp okio------------------------------------------#

#---------------------------------------   util code---------------------------------------#
-keep public class com.blankj.utilcode.util.**  {*; }
-keep class com.gyf.immersionbar.* {*;}
-dontwarn com.gyf.immersionbar.**
#-------------------------------------end  util code---------------------------------------#

#--------------------------------------- gson  ---------------------------------------#
-keep class sun.misc.Unsafe { *;}
-keep class com.google.gson.stream.** { *;}
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
#-------------------------------------end  gson---------------------------------------#

`

### How to build your mvvm with PerfactMvvm

**0、Don't forget add network permission.(I think you will not forgot...)**

`
    <uses-permission android:name="android.permission.INTERNET" />

`


**1、 Create a interface as retrofit service**
`
interface ApiService {
    companion object{
        const val BASE_URL = "your base url"
    }

    @GET(".....")
    suspend fun get...(.....): Bean
    
    //.....
    //other request
}

`


**2、 Create Model or Repository for target Activity,but I recommend to use single Repository with singleton model,like this:**
`
class BaseRepository {
    private var apiService: ApiService? = null

    init {
        if (null == apiService) {
            apiService = HttpHelper.getInstance().create(ApiService::class.java)
        }
    }

    companion object{
        val INSTANCE = BaseRepository()
    }

    suspend fun get...(...) : Bean? {
        return  apiService?.get...(format,n)
    }
}
`


**3、 Create a `ViewModel` for target Activity ,and extent `AbsViewModel<BaseRepository>`,and use `LiveData` to update view**
`
class MainViewModel : AbsViewModel<BaseRepository>() {
    init {
        mModel = BaseRepository.INSTANCE
    }
    
    val liveData = MutableLiveData<...>()

    fun getBingImage(){
        launch({
            loadState.postValue(LoadState.Loading("request"))
            val bingBeanRef = async { mModel?.get...(...) }
            bingBeanRef.await()?.let{
                liveData.postValue(it)
                loadState.postValue(LoadState.Success("request"))
            }
        },{
            loadState.postValue(LoadState.Fail(it.message ?: "error"))
        })
    }
}
`


**4、Create a `BaseActivity` and extend AbsLifecycleActivity**
`
abstract class BaseActivity<VM : AbsViewModel<BaseRepository>> : AbsLifecycleActivity<VM>(){

    override fun initStatusBar() {
        //setup status bar
    }

    protected fun initProgressDialog(){
        //init progress dialog
    }

    override fun showProgressDialog(){
        //show dialog
    }

    override fun hideProgressDialog(){
        //hide dialog
    }

    override fun dismissProgressDialog() {
        //dismiss dialog
    }
}
`


**5、Make `MainActivity`(other activity also) extend `BaseActivity`**
`
class MainActivity : BaseActivity<MainViewModel>() {
    private lateinit var viewBinding: ActivityMainBinding

    override fun dataObserver() {
        mViewModel?.liveData?.observe(this, Observer{
            //update ui with viewBinding
        })
    }

    override fun initView() {
        //init view and request data
        mViewModel?.get...()
    }

    override fun initClickListener() {

    }

    override fun initViewBinding(): View {
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = viewBinding.root
        view.setBackgroundColor(ContextCompat.getColor(this,R.color.colorAccent))
        //init your root view
        return view
    }

}
`


**6、Perfact!!!,you had build mvvm success**


**For more detail,you can download the example to learn**


