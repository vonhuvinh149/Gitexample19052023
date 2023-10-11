package com.android.mvvm.extension

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend inline fun CoroutineScope.launchOnIoO(crossinline scope: CoroutineScope.() -> Unit){
    withContext(Dispatchers.IO){scope.invoke(this)}
}

suspend inline fun CoroutineScope.launchOnMain(crossinline scope: CoroutineScope.() -> Unit){
    withContext(Dispatchers.Main){scope.invoke(this)}
}