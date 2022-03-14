package com.testme.feedpresentation.characterList.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testme.domain.DataSourceResult
import com.testme.domain.FlowUseCase
import com.testme.feeddomain.model.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton


@HiltViewModel
class CharactersListViewModel @Inject constructor(
  private val getCharactersUseCase: FlowUseCase<Unit, List<Character>>
) : ViewModel() {

  init {
    viewModelScope.launch {
      getCharactersUseCase(Unit).collect {
        when(it){
          is DataSourceResult.InProgress-> println("-> Loading")
          is DataSourceResult.Error ->  println("-> Error")
          is DataSourceResult.NoInternet ->  println("-> No Internet")
          is DataSourceResult.Success ->  println("-> New Data")
        }
      }
    }
  }

  fun printIt(): String = "Hello Sir"
}