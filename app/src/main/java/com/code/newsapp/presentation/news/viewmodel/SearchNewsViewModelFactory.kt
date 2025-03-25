package com.code.newsapp.presentation.news.viewmodel

//class SearchNewsViewModelFactory(
//    private val searchRepository: NewsSearchRepository,
//    val context: Context,
//    status: State<ConnectivityObserver.Status>,
//) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(SearchNewsViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return SearchNewsViewModel(searchRepository,context) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
//    }
//}